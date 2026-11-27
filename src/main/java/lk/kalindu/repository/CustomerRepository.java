package lk.kalindu.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.kalindu.entity.Customer;

@Stateless
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Customer findByEmail(String email) {
        return entityManager
                .createQuery(
                        "SELECT c FROM Customer c WHERE LOWER(c.email) = LOWER(:email)",
                        Customer.class
                )
                .setParameter("email", email.trim())
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void save(Customer customer) {
        entityManager.persist(customer);
    }
}
