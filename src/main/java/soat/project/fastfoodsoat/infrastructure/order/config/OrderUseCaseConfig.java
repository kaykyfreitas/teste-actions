package soat.project.fastfoodsoat.infrastructure.order.config;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soat.project.fastfoodsoat.application.gateway.*;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCase;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderUseCase;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff.ListOrderForStaffUseCase;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff.ListOrderForStaffUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.order.update.status.UpdateOrderStatusUseCase;
import soat.project.fastfoodsoat.application.usecase.order.update.status.UpdateOrderStatusUseCaseImpl;

@Configuration
public class OrderUseCaseConfig {

    private final OrderRepositoryGateway orderRepositoryGateway;
    private final ProductRepositoryGateway productRepositoryGateway;
    private final ClientRepositoryGateway clientRepositoryGateway;
    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final PaymentService paymentService;

    public OrderUseCaseConfig(final OrderRepositoryGateway orderRepositoryGateway,
                              final ProductRepositoryGateway productRepositoryGateway,
                              final ClientRepositoryGateway clientRepositoryGateway,
                              final PaymentRepositoryGateway paymentRepositoryGateway,
                              final PaymentService paymentService) {
        this.orderRepositoryGateway = orderRepositoryGateway;
        this.productRepositoryGateway = productRepositoryGateway;
        this.clientRepositoryGateway = clientRepositoryGateway;
        this.paymentRepositoryGateway = paymentRepositoryGateway;
        this.paymentService = paymentService;
    }

    @Bean
    @Transactional
    public CreateOrderUseCase createOrderUseCase() {
        return new CreateOrderUseCaseImpl(orderRepositoryGateway,
                                          productRepositoryGateway,
                                          clientRepositoryGateway,
                                          paymentRepositoryGateway,
                                          paymentService);
    }

    @Bean
    public ListOrderForStaffUseCase listOrderForStaffUseCase() {
        return new ListOrderForStaffUseCaseImpl(orderRepositoryGateway);
    }

    @Bean
    public ListOrderUseCase listOrderUseCase() {
        return new ListOrderUseCaseImpl(orderRepositoryGateway);
    }

    @Bean
    public UpdateOrderStatusUseCase updateOrderStatusUseCase() {
        return new UpdateOrderStatusUseCaseImpl(orderRepositoryGateway);
    }
}
