package soat.project.fastfoodsoat.infrastructure.staff.controller;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.staff.auth.AuthStaffCommand;
import soat.project.fastfoodsoat.application.usecase.staff.auth.AuthStaffUseCase;
import soat.project.fastfoodsoat.infrastructure.staff.model.request.AuthStaffRequest;
import soat.project.fastfoodsoat.infrastructure.staff.model.response.AuthStaffResponse;
import soat.project.fastfoodsoat.infrastructure.staff.presenter.AuthStaffPresenter;

@Component
public class AuthStaffControllerImpl implements AuthStaffController {

    private final AuthStaffUseCase authStaffUseCase;

    public AuthStaffControllerImpl(final AuthStaffUseCase authStaffUseCase) {
        this.authStaffUseCase = authStaffUseCase;
    }

    @Override
    public AuthStaffResponse authenticate(final AuthStaffRequest authRequest) {
        var command = new AuthStaffCommand(authRequest.identification());

        var output = this.authStaffUseCase.execute(command);

        return AuthStaffPresenter.present(output);
    }
}
