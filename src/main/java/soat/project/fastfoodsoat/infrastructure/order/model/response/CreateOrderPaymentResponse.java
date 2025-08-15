package soat.project.fastfoodsoat.infrastructure.order.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderPaymentResponse(
        @JsonProperty("status") String paymentStatus,
        @JsonProperty("external_reference") String paymentExternalReference,
        @JsonProperty("qr_code") String qrCode
) {
}
