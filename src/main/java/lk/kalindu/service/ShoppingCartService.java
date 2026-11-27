package lk.kalindu.service;

import jakarta.ejb.Stateful;
import lk.kalindu.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Stateful
public class ShoppingCartService {

    private List<Product> cart = new ArrayList<>();

    public void addProduct(Product product) {
        cart.add(product);
    }

    public List<Product> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}
