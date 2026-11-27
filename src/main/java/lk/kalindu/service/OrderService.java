package lk.kalindu.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lk.kalindu.entity.Customer;
import lk.kalindu.entity.OrderEntity;
import lk.kalindu.entity.Product;
import lk.kalindu.messaging.OrderProducer;
import lk.kalindu.repository.CustomerRepository;
import lk.kalindu.repository.OrderRepository;

@Stateless
public class OrderService {

    @Inject
    private InventoryService inventoryService;

    @Inject
    private StatisticsService statisticsService;

    @Inject
    private NotificationService notificationService;

    @Inject
    private OrderProducer orderProducer;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private ShoppingCartService shoppingCartService;

    @PostConstruct
    public void init() {
        System.out.println("OrderService Bean Created");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("OrderService Bean Destroyed");
    }

    public void placeOrder(Product product, int quantity, String email) {

        long start = System.currentTimeMillis();

        try {
            if (product == null) {
                throw new IllegalArgumentException("Product not found");
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }

            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email is required");
            }

            Customer customer = customerRepository.findByEmail(email);

            if (customer == null) {
                customer = new Customer();
                customer.setName("Online Customer");
                customer.setEmail(email);
                customerRepository.save(customer);
            }

            shoppingCartService.addProduct(product);

            OrderEntity order = new OrderEntity();
            order.setProductId(product);
            order.setCustomerId(customer);
            order.setQuantity(quantity);
            order.setStatus("PENDING");

            inventoryService.updateStock(product, quantity);

            orderRepository.save(order);

            shoppingCartService.clearCart();

            statisticsService.incrementOrders();

            try {
                notificationService.sendEmail(email);
            } catch (Exception e) {
                System.out.println("Email notification failed: " + e.getMessage());
            }

            try {
                orderProducer.sendMessage(product.getName());
            } catch (Exception e) {
                System.out.println("JMS message sending failed: " + e.getMessage());
            }

        } finally {
            long end = System.currentTimeMillis();
            statisticsService.addResponseTime(end - start);
        }
    }
}