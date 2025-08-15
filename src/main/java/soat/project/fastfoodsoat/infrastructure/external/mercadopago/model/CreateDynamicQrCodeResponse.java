package soat.project.fastfoodsoat.infrastructure.external.mercadopago.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateDynamicQrCodeResponse {
    @JsonProperty("qr_data")
    private String qr_data;

    @JsonProperty("in_store_order_id")
    private String in_store_order_id;

    public String getQrCode() {
        return qr_data;
    }
}
