package soat.project.fastfoodsoat.infrastructure.staff.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AuthStaffResponse(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf") String cpf,
        @JsonProperty("roles") List<String> roles
) {
}
