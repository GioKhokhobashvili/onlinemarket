package com.solvd.market.parser;

import com.solvd.market.customer.Address;
import com.solvd.market.customer.ContactInfo;
import com.solvd.market.customer.Customer;
import com.solvd.market.enums.DiscountTier;
import com.solvd.market.enums.OrderStatus;
import com.solvd.market.enums.PaymentType;
import com.solvd.market.enums.ProductCondition;
import com.solvd.market.enums.ShippingMethod;
import com.solvd.market.market.Market;
import com.solvd.market.order.Order;
import com.solvd.market.order.OrderItem;
import com.solvd.market.order.PaymentDetails;
import com.solvd.market.order.ShippingDetails;
import com.solvd.market.product.Category;
import com.solvd.market.product.DigitalProduct;
import com.solvd.market.product.Manufacturer;
import com.solvd.market.product.PhysicalProduct;
import com.solvd.market.product.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

/**
 * SAX-based parser for market.xml.
 *
 * <p>Useful XPath expressions for market.xml:
 * <ol>
 *   <li>{@code /market/name} - selects the market name.</li>
 *   <li>{@code /market/orders/order/customer/name} - selects every customer name.</li>
 *   <li>{@code /market/orders/order/orderItems/orderItem/product/price} - selects every product price.</li>
 *   <li>{@code /market/orders/order/orderItems/orderItem/product[@xsi:type='physicalProduct']/weightInKg} - selects weight of physical products only.</li>
 *   <li>{@code /market/orders/order[status='PENDING']/customer/contactInfo/email} - selects email of customers with pending orders.</li>
 *   <li>{@code //manufacturer[country='USA']/name} - selects names of all US manufacturers.</li>
 *   <li>{@code /market/orders/order/paymentDetails[paymentType='CREDIT_CARD']/transactionStatus} - selects transaction status for credit card payments.</li>
 * </ol>
 */
public class SaxMarketParser extends DefaultHandler implements Parser {

    private static final Logger LOGGER = LogManager.getLogger(SaxMarketParser.class);

    private Market market;
    private String marketName;
    private final List<Order> orders = new ArrayList<>();

    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;

    private String custName, custSurname;
    private int custAge;
    private DiscountTier discountTier;

    private String addrCity, addrStreet, addrZip;
    private String contactEmail, contactPhone;

    private final List<OrderItem> currentOrderItems = new ArrayList<>();
    private int itemQty;

    private String productType;
    private String prodName;
    private BigDecimal prodPrice;
    private ProductCondition prodCondition;
    private boolean prodInStock;
    private double prodWeight;
    private String prodDownloadLink;
    private String catName, catDesc;
    private String mfgName, mfgCountry;

    private int shipId;
    private String shipCourier, shipTracking;
    private ShippingMethod shipMethod;

    private int payId;
    private String payMethod, payTransStatus;
    private PaymentType payType;

    private final Deque<String> path = new ArrayDeque<>();
    private final StringBuilder chars = new StringBuilder();

    @Override
    public Market parse(String filePath) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(filePath), this);
        LOGGER.info("SAX parsing complete - market: {}, orders: {}", marketName, orders.size());
        return market;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        path.push(localName.isEmpty() ? qName : localName);
        chars.setLength(0);
        if ("product".equals(current())) {
            String t = attributes.getValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (t != null) productType = t;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        chars.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String tag = localName.isEmpty() ? qName : localName;
        String value = chars.toString().trim();
        path.pop();
        String parent = path.isEmpty() ? "" : path.peek();

        switch (tag) {
            case "market":
                market = new Market(marketName, new HashSet<>(orders));
                break;
            case "name":
                if ("market".equals(parent))            { marketName = value; }
                else if ("customer".equals(parent))     { custName = value; }
                else if ("category".equals(parent))     { catName = value; }
                else if ("manufacturer".equals(parent)) { mfgName = value; }
                else if ("product".equals(parent))      { prodName = value; }
                break;
            case "orderDateTime":
                orderDateTime = LocalDateTime.parse(value);
                break;
            case "status":
                orderStatus = OrderStatus.valueOf(value);
                break;
            case "order":
                Address addr = new Address(addrCity, addrStreet, addrZip);
                ContactInfo contact = new ContactInfo(contactEmail, contactPhone);
                Customer cust = new Customer(custName, custSurname, addr, contact, custAge, discountTier);
                ShippingDetails shipping = new ShippingDetails(shipId, shipCourier, shipTracking, shipMethod);
                PaymentDetails payment = new PaymentDetails(payId, payMethod, payTransStatus, payType);
                orders.add(new Order(orderDateTime, cust, new ArrayList<>(currentOrderItems), shipping, payment, orderStatus));
                currentOrderItems.clear();
                break;
            case "surname":
                custSurname = value;
                break;
            case "age":
                custAge = Integer.parseInt(value);
                break;
            case "discountTier":
                discountTier = DiscountTier.valueOf(value);
                break;
            case "city":
                addrCity = value;
                break;
            case "street":
                addrStreet = value;
                break;
            case "zipCode":
                addrZip = value;
                break;
            case "email":
                contactEmail = value;
                break;
            case "phone":
                contactPhone = value;
                break;
            case "quantity":
                itemQty = Integer.parseInt(value);
                break;
            case "product":
                Category cat = new Category(catName, catDesc);
                Manufacturer mfg = new Manufacturer(mfgName, mfgCountry);
                Product product;
                if ("physicalProduct".equals(productType)) {
                    product = new PhysicalProduct(prodName, prodPrice, cat, mfg, prodWeight, prodCondition);
                } else {
                    product = new DigitalProduct(prodName, prodPrice, cat, mfg, prodDownloadLink, prodCondition);
                }
                currentOrderItems.add(new OrderItem(product, itemQty));
                break;
            case "price":
                prodPrice = new BigDecimal(value);
                break;
            case "condition":
                prodCondition = ProductCondition.valueOf(value);
                break;
            case "inStock":
                prodInStock = Boolean.parseBoolean(value);
                break;
            case "weightInKg":
                prodWeight = Double.parseDouble(value);
                break;
            case "downloadLink":
                prodDownloadLink = value;
                break;
            case "description":
                catDesc = value;
                break;
            case "country":
                mfgCountry = value;
                break;
            case "courierName":
                shipCourier = value;
                break;
            case "trackingNumber":
                shipTracking = value;
                break;
            case "shippingMethod":
                shipMethod = ShippingMethod.valueOf(value);
                break;
            case "paymentMethod":
                payMethod = value;
                break;
            case "transactionStatus":
                payTransStatus = value;
                break;
            case "paymentType":
                payType = PaymentType.valueOf(value);
                break;
            case "id":
                if ("shippingDetails".equals(parent))     { shipId = Integer.parseInt(value); }
                else if ("paymentDetails".equals(parent)) { payId = Integer.parseInt(value); }
                break;
        }
        chars.setLength(0);
    }

    private String current() {
        return path.isEmpty() ? "" : path.peek();
    }
}