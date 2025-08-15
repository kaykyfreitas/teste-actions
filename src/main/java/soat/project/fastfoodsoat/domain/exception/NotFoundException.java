package soat.project.fastfoodsoat.domain.exception;


import soat.project.fastfoodsoat.domain.shared.Entity;
import soat.project.fastfoodsoat.domain.shared.Identifier;
import soat.project.fastfoodsoat.domain.shared.PublicIdentifier;
import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<DomainError> someErrors) {
        super(aMessage, someErrors);
    }

    public static NotFoundException with(
            final Class<? extends Entity<?>> entity,
            final Identifier id
    ) {
        final var errorMessage = "%s with id %s was not found".formatted(
                entity.getSimpleName().toLowerCase(),
                id.getValue()
        );
        final var domainError = new DomainError(errorMessage);
        return new NotFoundException(errorMessage, List.of(domainError));
    }

    public static NotFoundException with(
            final Class<? extends Entity<?>> entity,
            final PublicIdentifier id
    ) {
        final var errorMessage = "%s with id %s was not found".formatted(
                entity.getSimpleName().toLowerCase(),
                id.getValue()
        );
        final var domainError = new DomainError(errorMessage);
        return new NotFoundException(errorMessage, List.of(domainError));
    }

    public static NotFoundException with(final DomainError anError) {
        return new NotFoundException(anError.message(), List.of(anError));
    }

}
