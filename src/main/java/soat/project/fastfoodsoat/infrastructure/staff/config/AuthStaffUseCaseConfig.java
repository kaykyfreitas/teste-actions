package soat.project.fastfoodsoat.infrastructure.staff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soat.project.fastfoodsoat.application.gateway.StaffRepositoryGateway;
import soat.project.fastfoodsoat.application.usecase.staff.auth.AuthStaffUseCaseImpl;

@Configuration
public class AuthStaffUseCaseConfig {

    private final StaffRepositoryGateway staffRepositoryGateway;

    public AuthStaffUseCaseConfig(final StaffRepositoryGateway staffRepositoryGateway) {
        this.staffRepositoryGateway = staffRepositoryGateway;
    }

    @Bean
    public AuthStaffUseCaseImpl authStaffUseCase() {
        return new AuthStaffUseCaseImpl(staffRepositoryGateway);
    }

}
