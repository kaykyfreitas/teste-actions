package soat.project.fastfoodsoat.infrastructure.staff.presenter;

import soat.project.fastfoodsoat.infrastructure.client.model.response.ClientAuthResponse;
import soat.project.fastfoodsoat.application.output.client.auth.AuthClientOutput;

public interface AuthClientPresenter {
    static ClientAuthResponse present(final AuthClientOutput output) {
        return new ClientAuthResponse(
                output.publicId(),
                output.name(),
                output.email(),
                output.cpf()
        );
    }
}
