package soat.project.fastfoodsoat.application.usecase.staff.auth;

import soat.project.fastfoodsoat.application.command.staff.auth.AuthStaffCommand;
import soat.project.fastfoodsoat.application.gateway.StaffRepositoryGateway;
import soat.project.fastfoodsoat.application.output.staff.auth.AuthStaffOutput;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.util.Objects;

public class AuthStaffUseCaseImpl extends AuthStaffUseCase {

    private final StaffRepositoryGateway staffRepositoryGateway;

    public AuthStaffUseCaseImpl(
            final StaffRepositoryGateway staffRepositoryGateway
    ) {
        this.staffRepositoryGateway = staffRepositoryGateway;
    }

    @Override
    public AuthStaffOutput execute(final AuthStaffCommand command) {
        final var identification = command.identification();

        if (Objects.isNull(identification) || identification.isBlank()) {
            throw DomainException.with(new DomainError("invalid identification"));
        }

        final var staff = retrieveStaff(identification);

        if (!staff.isActive()) {
            throw DomainException.with(new DomainError("staff is not active"));
        }

        return AuthStaffOutput.from(staff);
    }

    private Staff retrieveStaff(final String identification) {
        var staff = this.staffRepositoryGateway.findByEmail(identification);

        if (staff.isPresent()) return staff.get();

        staff = this.staffRepositoryGateway.findByCpf(StaffCpf.of(identification));

        if (staff.isPresent()) return staff.get();

        throw NotFoundException.with(new DomainError("staff not found"));
    }

}
