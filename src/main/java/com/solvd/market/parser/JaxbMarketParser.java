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
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class JaxbMarketParser implements Parser {

    private static final Logger LOGGER = LogManager.getLogger(JaxbMarketParser.class);

    @Override
    public Market parse(String filePath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(MarketDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        MarketDto dto = (MarketDto) unmarshaller.unmarshal(new File(filePath));
        Market market = toMarket(dto);
        LOGGER.info("JAXB parsing complete - market: {}", market.getName());
        return market;
    }

    private Market toMarket(MarketDto dto) {
        List<Order> orders = dto.orders.stream().map(this::toOrder).collect(Collectors.toList());
        return new Market(dto.name, new HashSet<>(orders));
    }

    private Order toOrder(OrderDto dto) {
        Address addr = new Address(dto.customer.address.city, dto.customer.address.street, dto.customer.address.zipCode);
        ContactInfo contact = new ContactInfo(dto.customer.contactInfo.email, dto.customer.contactInfo.phone);
        Customer customer = new Customer(dto.customer.name, dto.customer.surname, addr, contact, dto.customer.age, DiscountTier.valueOf(dto.customer.discountTier));
        List<OrderItem> items = dto.orderItems.stream().map(this::toOrderItem).collect(Collectors.toList());
        ShippingDetails shipping = new ShippingDetails(dto.shippingDetails.id, dto.shippingDetails.courierName, dto.shippingDetails.trackingNumber, ShippingMethod.valueOf(dto.shippingDetails.shippingMethod));
        PaymentDetails payment = new PaymentDetails(dto.paymentDetails.id, dto.paymentDetails.paymentMethod, dto.paymentDetails.transactionStatus, PaymentType.valueOf(dto.paymentDetails.paymentType));
        return new Order(LocalDateTime.parse(dto.orderDateTime), customer, items, shipping, payment, OrderStatus.valueOf(dto.status));
    }

    private OrderItem toOrderItem(OrderItemDto dto) {
        Category cat = new Category(dto.product.category.name, dto.product.category.description);
        Manufacturer mfg = new Manufacturer(dto.product.manufacturer.name, dto.product.manufacturer.country);
        BigDecimal price = new BigDecimal(dto.product.price);
        ProductCondition condition = ProductCondition.valueOf(dto.product.condition);
        Product product;
        if ("physicalProduct".equals(dto.product.type)) {
            product = new PhysicalProduct(dto.product.name, price, cat, mfg, Double.parseDouble(dto.product.weightInKg), condition);
        } else {
            product = new DigitalProduct(dto.product.name, price, cat, mfg, dto.product.downloadLink, condition);
        }
        return new OrderItem(product, dto.quantity);
    }

    @XmlRootElement(name = "market")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MarketDto {
        public String name;
        @XmlElementWrapper(name = "orders")
        @XmlElement(name = "order")
        public List<OrderDto> orders = new ArrayList<>();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderDto {
        public String orderDateTime;
        public String status;
        public CustomerDto customer;
        @XmlElementWrapper(name = "orderItems")
        @XmlElement(name = "orderItem")
        public List<OrderItemDto> orderItems = new ArrayList<>();
        public ShippingDetailsDto shippingDetails;
        public PaymentDetailsDto paymentDetails;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CustomerDto {
        public String name, surname;
        public int age;
        public String discountTier;
        public AddressDto address;
        public ContactInfoDto contactInfo;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AddressDto {
        public String city, street, zipCode;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ContactInfoDto {
        public String email, phone;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderItemDto {
        public int quantity;
        public ProductDto product;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ProductDto {
        @XmlAttribute(namespace = "http://www.w3.org/2001/XMLSchema-instance", name = "type")
        public String type;
        public String name, price, condition, inStock, weightInKg, downloadLink;
        public CategoryDto category;
        public ManufacturerDto manufacturer;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CategoryDto {
        public String name, description;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ManufacturerDto {
        public String name, country;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ShippingDetailsDto {
        public int id;
        public String courierName, trackingNumber, shippingMethod;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PaymentDetailsDto {
        public int id;
        public String paymentMethod, transactionStatus, paymentType;
    }
}