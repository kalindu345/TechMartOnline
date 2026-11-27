package lk.kalindu.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lk.kalindu.entity.Product;
import lk.kalindu.repository.ProductRepository;

@Stateless
public class InventoryService {

    @Inject
    private ProductRepository productRepository;

    public void updateStock(Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock available");
        }

        int currentStock = product.getStock();
        product.setStock(currentStock - quantity);

        productRepository.update(product);
    }
}
