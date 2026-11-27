package lk.kalindu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;
    private String status;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product productId;
    public OrderEntity() {
    }

    public OrderEntity(int id, int quantity, String status, Customer customerId, Product productId) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }
}
