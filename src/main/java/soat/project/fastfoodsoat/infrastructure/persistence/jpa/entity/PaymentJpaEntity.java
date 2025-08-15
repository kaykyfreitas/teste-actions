package soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", columnDefinition = "decimal(10,2)")
    private BigDecimal value;

    @Column(name = "external_reference", nullable = false)
    private String externalReference;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private OrderJpaEntity order;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public PaymentJpaEntity() {}

    public PaymentJpaEntity(Integer id,
                            BigDecimal value,
                            String externalReference,
                            String qrCode,
                            PaymentStatus status,
                            OrderJpaEntity order,
                            Instant createdAt,
                            Instant updatedAt,
                            Instant deletedAt) {
        this.id = id;
        this.value = value;
        this.externalReference = externalReference;
        this.qrCode = qrCode;
        this.status = status;
        this.order = order;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public OrderJpaEntity getOrder() {
        return order;
    }

    public void setOrder(OrderJpaEntity savedOrder) {
        if (savedOrder == null) return;

        this.order = savedOrder;
    }
}
