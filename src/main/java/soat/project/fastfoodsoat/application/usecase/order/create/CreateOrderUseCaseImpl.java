package soat.project.fastfoodsoat.application.usecase.order.create;

import soat.project.fastfoodsoat.application.command.order.create.CreateOrderCommand;
import soat.project.fastfoodsoat.application.command.order.create.CreateOrderProductCommand;
import soat.project.fastfoodsoat.application.gateway.*;
import soat.project.fastfoodsoat.application.output.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientPublicId;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreateOrderUseCaseImpl extends CreateOrderUseCase {

    private final OrderRepositoryGateway orderRepositoryGateway;
    private final ProductRepositoryGateway productRepositoryGateway;
    private final ClientRepositoryGateway clientRepositoryGateway;
    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final PaymentService paymentService;

    public CreateOrderUseCaseImpl(final OrderRepositoryGateway orderRepositoryGateway,
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

    @Override
    public CreateOrderOutput execute(final CreateOrderCommand command) {
        final ClientPublicId clientPublicId = command.clientPublicId() != null ?
                ClientPublicId.of(command.clientPublicId()) : null;

        final var client = getClientByPublicId(clientPublicId);

        final Notification notification = Notification.create();

        final List<OrderProduct> orderProductDomains = buildOrderProducts(command.orderProducts(), notification);

        final BigDecimal value = Order.calculateValue(orderProductDomains);
        final Integer orderNumber = orderRepositoryGateway.findLastOrderNumber() + 1;
        final UUID publicId = UUID.randomUUID();
        final UUID externalReference = UUID.randomUUID();

        final var clientId = client != null ? client.getId() : null;

        final Order order = notification.validate(() ->
                Order.newOrder(
                        OrderPublicId.of(publicId),
                        orderNumber,
                        OrderStatus.RECEIVED,
                        clientId,
                        value,
                        orderProductDomains,
                        null
                )
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create order", notification);
        }

        final Order createdOrder = orderRepositoryGateway.create(order);

        final String qrCodeText = paymentService.createDynamicQrCode(
                orderNumber,
                externalReference,
                value,
                createdOrder.getOrderProducts()
        );

        if (qrCodeText == null) {
            throw new NotificationException("could not create qr code", notification);
        }

        final Payment payment = notification.validate(() ->
                Payment.newPayment(
                        value,
                        externalReference.toString(),
                        qrCodeText,
                        PaymentStatus.PENDING,
                        createdOrder
                )
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create payment", notification);
        }

        final Payment createdPayment = paymentRepositoryGateway.create(payment);

        return CreateOrderOutput.from(
                createdOrder,
                createdPayment
        );
    }

    private Client getClientByPublicId(final ClientPublicId clientPublicId) {
        if (clientPublicId == null) return null;

        return clientRepositoryGateway.findByPublicId(clientPublicId)
                .orElseThrow(() -> NotFoundException.with(Client.class, clientPublicId));
    }

    private List<OrderProduct> buildOrderProducts(
            final List<CreateOrderProductCommand> orderProductCommands,
            final Notification notification
    ) {
        if (orderProductCommands.isEmpty()) {
            throw new NotificationException("Order must have at least one product", notification);
        }

        final List<Product> products = productRepositoryGateway.findByIds(
                orderProductCommands.stream().map(CreateOrderProductCommand::productId).toList()
        );

        return orderProductCommands.stream().map(orderProduct -> {
            final Product product = products.stream()
                .filter(p -> p.getId().getValue().equals(orderProduct.productId()))
                .findFirst()
                .orElseThrow(() ->  NotFoundException.with(Product.class, new ProductId(orderProduct.productId())));

            final BigDecimal orderProductValue = product.getValue()
                .multiply(BigDecimal.valueOf(orderProduct.quantity()));

            return OrderProduct.newOrderProduct(
                orderProductValue,
                orderProduct.quantity(),
                product
            );
        })
        .toList();
    }
}
