package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderCommand;
import soat.project.fastfoodsoat.application.gateway.OrderRepositoryGateway;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderOutput;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

public class ListOrderUseCaseImpl extends ListOrderUseCase {

    private final OrderRepositoryGateway orderRepositoryGateway;

    public ListOrderUseCaseImpl(final OrderRepositoryGateway orderRepositoryGateway) {
        this.orderRepositoryGateway = orderRepositoryGateway;
    }

    @Override
    public Pagination<ListOrderOutput> execute(final ListOrderCommand params) {
        final var query = params.searchQuery();

        return orderRepositoryGateway.findAll(query)
                .map(ListOrderOutput::from);
    }
}
