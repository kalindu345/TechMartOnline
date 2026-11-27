package lk.kalindu.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.kalindu.dto.OrderRequest;
import lk.kalindu.entity.Product;
import lk.kalindu.repository.ProductRepository;
import lk.kalindu.service.OrderService;

@Path("/orders")
public class OrderResource {

    @Inject
    private OrderService orderService;

    @Inject
    private ProductRepository productRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(OrderRequest request) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Order request cannot be empty")
                        .build();
            }

            if (request.getQuantity() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Quantity must be greater than zero")
                        .build();
            }

            if (request.getEmail() == null || request.getEmail().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Email is required")
                        .build();
            }

            Product product = productRepository.findById(request.getProductId());

            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Product not found")
                        .build();
            }

            orderService.placeOrder(
                    product,
                    request.getQuantity(),
                    request.getEmail()
            );

            return Response.status(Response.Status.CREATED)
                    .entity("Order placed successfully")
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.serverError()
                    .entity("Order placement failed: " + e.getMessage())
                    .build();
        }
    }
}