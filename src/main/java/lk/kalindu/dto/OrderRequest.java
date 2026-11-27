package lk.kalindu.dto;

public class OrderRequest {

    private int customerId;
    private int productId;
    private int quantity;
    private String email;


    public OrderRequest() {
    }

    public OrderRequest(int customerId, int productId, int quantity, String email) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
