package lk.kalindu.resource;

import jakarta.ws.rs.core.Response;
import lk.kalindu.entity.Product;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lk.kalindu.repository.ProductRepository;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private ProductRepository productRepository;

    @POST
    public Response saveProduct(Product product) {
        if (product == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Product cannot be empty")
                    .build();
        }

        if (product.getName() == null || product.getName().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Product name is required")
                    .build();
        }

        if (product.getPrice() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Product price must be greater than zero")
                    .build();
        }

        if (product.getStock() < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Stock cannot be negative")
                    .build();
        }

        productRepository.save(product);

        return Response.status(Response.Status.CREATED)
                .entity("Product saved successfully")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") int id) {
        Product product = productRepository.findById(id);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product not found")
                    .build();
        }

        return Response.ok(product).build();
    }

    @GET
    public List<Product> getAllProducts(){

        return productRepository.findAll();

    }
    @PUT
    public void updateProduct(Product product){

        productRepository.update(product);

    }

    @DELETE
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") int id){

        productRepository.delete(id);

    }

}
