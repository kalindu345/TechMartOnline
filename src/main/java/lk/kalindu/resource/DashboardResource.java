package lk.kalindu.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lk.kalindu.dto.MetricsDTO;
import lk.kalindu.repository.ProductRepository;
import lk.kalindu.service.StatisticsService;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    private StatisticsService statisticsService;

    @Inject
    private ProductRepository productRepository;

    @GET
    public MetricsDTO getMetrics() {

        MetricsDTO dto = new MetricsDTO();

        dto.setTotalOrders(
                statisticsService.getTotalOrders());

        dto.setMessagesProcessed(
                statisticsService.getMessagesProcessed());

        dto.setEmailsSent(
                statisticsService.getEmailsSent());

        dto.setAverageResponseTime(
                statisticsService.getAverageResponseTime());

        dto.setProductsAvailable(
                productRepository.getProductCount());

        dto.setServerStatus("RUNNING");

        return dto;

    }

}
