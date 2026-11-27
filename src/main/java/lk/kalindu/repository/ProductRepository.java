package lk.kalindu.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.kalindu.entity.Product;

import java.util.List;

@Stateless
public class ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Product product){ //Insert

        entityManager.persist(product);

    }
    public Product findById(int id){ //Search

        return entityManager.find(Product.class,id);

    }
    public void delete(int id) {
        Product product = entityManager.find(Product.class, id);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        entityManager.remove(product);
    }
    public void update(Product product){//Update

        entityManager.merge(product);

    }

    public List<Product> findAll(){

        return entityManager
                .createQuery(
                        "SELECT p FROM Product p",
                        Product.class)
                .getResultList();

    }

    public int getProductCount(){

        Long count = entityManager
                .createQuery(
                        "SELECT COUNT(p) FROM Product p",
                        Long.class)
                .getSingleResult();

        return count.intValue();

    }
}
