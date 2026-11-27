package lk.kalindu.test;

import lk.kalindu.entity.Product;
import lk.kalindu.repository.ProductRepository;
import lk.kalindu.service.InventoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateStock() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        inventoryService.updateStock(product, 3);

        assertEquals(7, product.getStock());

        verify(productRepository, times(1))
                .update(product);
    }

    @Test
    void testUpdateStockWithInsufficientStock() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(2);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.updateStock(product, 5)
        );

        assertEquals("Insufficient stock available", exception.getMessage());

        verify(productRepository, never())
                .update(any());
    }

    @Test
    void testUpdateStockWithInvalidQuantity() {

        Product product = new Product();
        product.setName("Laptop");
        product.setStock(10);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.updateStock(product, 0)
        );

        assertEquals("Quantity must be greater than zero", exception.getMessage());

        verify(productRepository, never())
                .update(any());
    }

    @Test
    void testUpdateStockWithNullProduct() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.updateStock(null, 2)
        );

        assertEquals("Product cannot be null", exception.getMessage());

        verify(productRepository, never())
                .update(any());
    }
}
