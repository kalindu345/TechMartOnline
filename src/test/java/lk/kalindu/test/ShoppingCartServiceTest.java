package lk.kalindu.test;

import lk.kalindu.entity.Product;
import lk.kalindu.service.ShoppingCartService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartServiceTest {

    @Test
    void testAddProduct() {

        ShoppingCartService cart =
                new ShoppingCartService();

        Product product =
                new Product();

        cart.addProduct(product);

        assertEquals(
                1,
                cart.getCart().size());
    }

    @Test
    void testClearCart() {

        ShoppingCartService cart =
                new ShoppingCartService();

        Product product =
                new Product();

        cart.addProduct(product);

        cart.clearCart();

        assertEquals(
                0,
                cart.getCart().size());
    }
}
