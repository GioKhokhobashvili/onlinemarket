package com.solvd.market.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class JacksonMarketParser implements Parser {

    private static final Logger LOGGER = LogManager.getLogger(JacksonMarketParser.class);

    @Override
    public Market parse(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MarketDto dto = mapper.readValue(new File(filePath), MarketDto.class);
        Market market = toMarket(dto);
        LOGGER.info("Jackson parsing complete - market: {}", market.getName());
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
        BigDecimal price = BigDecimal.valueOf(dto.product.price);
        ProductCondition condition = ProductCondition.valueOf(dto.product.condition);
        Product product;
        if ("physicalProduct".equals(dto.product.type)) {
            product = new PhysicalProduct(dto.product.name, price, cat, mfg, dto.product.weightInKg, condition);
        } else {
            product = new DigitalProduct(dto.product.name, price, cat, mfg, dto.product.downloadLink, condition);
        }
        return new OrderItem(product, dto.quantity);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarketDto {
        public String name;
        public List<OrderDto> orders = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderDto {
        public String orderDateTime;
        public String status;
        public CustomerDto customer;
        public List<OrderItemDto> orderItems = new ArrayList<>();
        public ShippingDetailsDto shippingDetails;
        public PaymentDetailsDto paymentDetails;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerDto {
        public String name, surname;
        public int age;
        public String discountTier;
        public AddressDto address;
        public ContactInfoDto contactInfo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressDto {
        public String city, street, zipCode;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContactInfoDto {
        public String email, phone;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderItemDto {
        public int quantity;
        public ProductDto product;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductDto {
        public String type;
        public String name, condition, downloadLink;
        public double price;
        public boolean inStock;
        public double weightInKg;
        public CategoryDto category;
        public ManufacturerDto manufacturer;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryDto {
        public String name, description;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ManufacturerDto {
        public String name, country;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShippingDetailsDto {
        public int id;
        public String courierName, trackingNumber, shippingMethod;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentDetailsDto {
        public int id;
        public String paymentMethod, transactionStatus, paymentType;
    }
}