package soat.project.fastfoodsoat.application.output.staff.auth;

import soat.project.fastfoodsoat.domain.role.Role;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;

import java.util.List;

public record AuthStaffOutput(
        String name,
        String email,
        String cpf,
        List<String> roles
) {

    public static AuthStaffOutput from(final Staff staff) {
        return new AuthStaffOutput(
                staff.getName(),
                staff.getEmail(),
                staff.getCpf() != null ? staff.getCpf().getValue() : null,
                staff.getRoles().stream().map(Role::getName).toList()
        );
    }

}
