package lk.kalindu.service;


import jakarta.ejb.Singleton;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class StatisticsService {

    private AtomicInteger totalOrders = new AtomicInteger(0);
    private AtomicInteger messagesProcessed = new AtomicInteger(0);
    private AtomicInteger emailsSent = new AtomicInteger(0);
    private AtomicLong totalResponseTime = new AtomicLong(0);

    public void incrementOrders() {
        totalOrders.incrementAndGet();
    }

    public void incrementMessagesProcessed() {
        messagesProcessed.incrementAndGet();
    }

    public void incrementEmailsSent() {
        emailsSent.incrementAndGet();
    }

    public void addResponseTime(long responseTime) {
        totalResponseTime.addAndGet(responseTime);
    }

    public int getTotalOrders() {
        return totalOrders.get();
    }

    public int getMessagesProcessed() {
        return messagesProcessed.get();
    }

    public int getEmailsSent() {
        return emailsSent.get();
    }

    public long getAverageResponseTime() {
        if (totalOrders.get() == 0) {
            return 0;
        }

        return totalResponseTime.get() / totalOrders.get();
    }
}
