package soat.project.fastfoodsoat.infrastructure.staff.controller;

import soat.project.fastfoodsoat.infrastructure.staff.model.request.AuthStaffRequest;
import soat.project.fastfoodsoat.infrastructure.staff.model.response.AuthStaffResponse;

public interface AuthStaffController {
    AuthStaffResponse authenticate(AuthStaffRequest request);
}
