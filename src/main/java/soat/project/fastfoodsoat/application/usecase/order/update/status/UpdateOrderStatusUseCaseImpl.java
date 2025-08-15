package soat.project.fastfoodsoat.application.usecase.order.update.status;

import soat.project.fastfoodsoat.application.command.order.update.status.UpdateOrderStatusCommand;
import soat.project.fastfoodsoat.application.gateway.OrderRepositoryGateway;
import soat.project.fastfoodsoat.application.output.order.update.status.UpdateOrderStatusOutput;
import soat.project.fastfoodsoat.domain.exception.IllegalStateException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.util.Objects;

public class UpdateOrderStatusUseCaseImpl extends UpdateOrderStatusUseCase {

    private final OrderRepositoryGateway orderRepositoryGateway;

    public UpdateOrderStatusUseCaseImpl(final OrderRepositoryGateway orderRepositoryGateway) {
        this.orderRepositoryGateway = Objects.requireNonNull(orderRepositoryGateway);
    }

    @Override
    public UpdateOrderStatusOutput execute(final UpdateOrderStatusCommand command) {
        final var publicId = OrderPublicId.of(command.orderPublicId());

        final var order = orderRepositoryGateway.findByPublicId(publicId)
                .orElseThrow(() -> NotFoundException.with(Order.class, publicId));

        final var newStatus = validateStatus(command.newStatus());

        final var updatedOrder = order.updateStatus(
                newStatus
        );

        final var savedOrder = orderRepositoryGateway.update(updatedOrder);

        return UpdateOrderStatusOutput.from(savedOrder);
    }

    public OrderStatus validateStatus(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw IllegalStateException.with(new DomainError("Status inválido: " + status));
        }
    }

}
