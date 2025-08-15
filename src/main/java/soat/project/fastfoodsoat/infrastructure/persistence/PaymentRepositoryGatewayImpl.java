package soat.project.fastfoodsoat.infrastructure.persistence;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.PaymentJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.mapper.PaymentJpaMapper;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.PaymentRepository;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;

import java.util.Optional;

@Component
public class PaymentRepositoryGatewayImpl implements PaymentRepositoryGateway {

    private final PaymentRepository paymentRepository;

    public PaymentRepositoryGatewayImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment create(Payment payment) {
        return save(payment);
    }

    @Override
    public Payment update(Payment payment) {
        return save(payment);
    }

    @Override
    public Optional<Payment> findByExternalReference(String externalReference) {
        return paymentRepository
                .findByExternalReference(externalReference)
                .map(PaymentJpaMapper::fromJpa);
    }

    private Payment save(Payment payment) {
        final PaymentJpaEntity paymentJpa = PaymentJpaMapper.toJpa(payment);

        return PaymentJpaMapper.fromJpa(paymentRepository.save(paymentJpa));
    }
}
