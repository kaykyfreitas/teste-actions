package soat.project.fastfoodsoat.infrastructure.staff.presenter;

import soat.project.fastfoodsoat.infrastructure.staff.model.response.AuthStaffResponse;
import soat.project.fastfoodsoat.application.output.staff.auth.AuthStaffOutput;

public interface AuthStaffPresenter {

    static AuthStaffResponse present(final AuthStaffOutput output) {
        return new AuthStaffResponse(
                output.name(),
                output.email(),
                output.cpf(),
                output.roles()
        );
    }

}
