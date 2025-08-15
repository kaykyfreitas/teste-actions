package soat.project.fastfoodsoat.infrastructure.order.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderProductResponse(
        @JsonProperty("product_id") Integer productId,
        @JsonProperty("product_name") String productName,
        @JsonProperty("quantity") Integer quantity
) {
}
