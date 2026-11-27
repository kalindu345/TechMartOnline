package lk.kalindu.test;

import lk.kalindu.service.StatisticsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    @Test
    void testIncrementOrders() {

        StatisticsService statistics =
                new StatisticsService();

        statistics.incrementOrders();

        statistics.incrementOrders();

        assertEquals(
                2,
                statistics.getTotalOrders());

    }

    @Test
    void testAverageResponseTime() {

        StatisticsService statistics =
                new StatisticsService();

        statistics.incrementOrders();
        statistics.incrementOrders();

        statistics.addResponseTime(100);
        statistics.addResponseTime(200);

        assertEquals(
                150,
                statistics.getAverageResponseTime());

    }

}
