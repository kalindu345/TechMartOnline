package lk.kalindu.test;

import lk.kalindu.entity.Customer;
import lk.kalindu.entity.Product;
import lk.kalindu.messaging.OrderProducer;
import lk.kalindu.repository.CustomerRepository;
import lk.kalindu.repository.OrderRepository;
import lk.kalindu.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private InventoryService inventoryService;

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private OrderProducer orderProducer;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrderSuccessfully() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Kalindu");
        customer.setEmail("john@gmail.com");

        when(customerRepository.findByEmail("john@gmail.com"))
                .thenReturn(customer);

        orderService.placeOrder(
                product,
                2,
                "john@gmail.com"
        );

        verify(customerRepository, times(1))
                .findByEmail("john@gmail.com");

        verify(customerRepository, never())
                .save(any());

        verify(shoppingCartService, times(1))
                .addProduct(product);

        verify(inventoryService, times(1))
                .updateStock(product, 2);

        verify(orderRepository, times(1))
                .save(any());

        verify(shoppingCartService, times(1))
                .clearCart();

        verify(statisticsService, times(1))
                .incrementOrders();

        verify(notificationService, times(1))
                .sendEmail("john@gmail.com");

        verify(orderProducer, times(1))
                .sendMessage("Laptop");
    }

    @Test
    void testPlaceOrderWithNewCustomer() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        when(customerRepository.findByEmail("new@gmail.com"))
                .thenReturn(null);

        orderService.placeOrder(
                product,
                2,
                "new@gmail.com"
        );

        verify(customerRepository, times(1))
                .findByEmail("new@gmail.com");

        verify(customerRepository, times(1))
                .save(any(Customer.class));

        verify(shoppingCartService, times(1))
                .addProduct(product);

        verify(inventoryService, times(1))
                .updateStock(product, 2);

        verify(orderRepository, times(1))
                .save(any());

        verify(shoppingCartService, times(1))
                .clearCart();
    }

    @Test
    void testPlaceOrderWithNullProduct() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.placeOrder(
                        null,
                        2,
                        "john@gmail.com"
                )
        );

        assertEquals("Product not found", exception.getMessage());

        verify(customerRepository, never()).findByEmail(anyString());
        verify(shoppingCartService, never()).addProduct(any());
        verify(orderRepository, never()).save(any());
        verify(inventoryService, never()).updateStock(any(), anyInt());
        verify(orderProducer, never()).sendMessage(anyString());
    }

    @Test
    void testPlaceOrderWithInvalidQuantity() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.placeOrder(
                        product,
                        0,
                        "john@gmail.com"
                )
        );

        assertEquals("Quantity must be greater than zero", exception.getMessage());

        verify(customerRepository, never()).findByEmail(anyString());
        verify(shoppingCartService, never()).addProduct(any());
        verify(orderRepository, never()).save(any());
        verify(inventoryService, never()).updateStock(any(), anyInt());
        verify(orderProducer, never()).sendMessage(anyString());
    }

    @Test
    void testPlaceOrderWithEmptyEmail() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.placeOrder(
                        product,
                        2,
                        ""
                )
        );

        assertEquals("Email is required", exception.getMessage());

        verify(customerRepository, never()).findByEmail(anyString());
        verify(shoppingCartService, never()).addProduct(any());
        verify(orderRepository, never()).save(any());
        verify(inventoryService, never()).updateStock(any(), anyInt());
        verify(orderProducer, never()).sendMessage(anyString());
    }

    @Test
    void testNotificationFailureDoesNotStopOrderProcessing() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Kalindu");
        customer.setEmail("john@gmail.com");

        when(customerRepository.findByEmail("john@gmail.com"))
                .thenReturn(customer);

        doThrow(new RuntimeException("Email server error"))
                .when(notificationService)
                .sendEmail("john@gmail.com");

        orderService.placeOrder(
                product,
                2,
                "john@gmail.com"
        );

        verify(orderRepository, times(1))
                .save(any());

        verify(inventoryService, times(1))
                .updateStock(product, 2);

        verify(shoppingCartService, times(1))
                .addProduct(product);

        verify(shoppingCartService, times(1))
                .clearCart();

        verify(orderProducer, times(1))
                .sendMessage("Laptop");
    }
}