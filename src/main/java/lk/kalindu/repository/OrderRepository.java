package lk.kalindu.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.kalindu.entity.OrderEntity;

@Stateless
public class OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(OrderEntity order){

        entityManager.persist(order);

    }
}
