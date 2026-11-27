package lk.kalindu.dto;

public class MetricsDTO {
    private int totalOrders;
    private int productsAvailable;
    private int messagesProcessed;
    private int emailsSent;
    private long averageResponseTime;
    private String serverStatus;

    public MetricsDTO() {
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getProductsAvailable() {
        return productsAvailable;
    }

    public void setProductsAvailable(int productsAvailable) {
        this.productsAvailable = productsAvailable;
    }

    public int getMessagesProcessed() {
        return messagesProcessed;
    }

    public void setMessagesProcessed(int messagesProcessed) {
        this.messagesProcessed = messagesProcessed;
    }

    public int getEmailsSent() {
        return emailsSent;
    }

    public void setEmailsSent(int emailsSent) {
        this.emailsSent = emailsSent;
    }

    public long getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(long averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }
}
