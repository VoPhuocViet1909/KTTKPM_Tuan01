package iuh.fit.se.ex01ordermanagement.state;

import iuh.fit.se.ex01ordermanagement.context.OrderContext;
import iuh.fit.se.ex01ordermanagement.decorator.LoggingDecorator;
import iuh.fit.se.ex01ordermanagement.strategy.OrderStrategy;
import iuh.fit.se.ex01ordermanagement.strategy.ProcessingOrderStrategy;

public class ProcessingState implements OrderState {

    @Override
    public void handle(OrderContext context) {

    OrderStrategy strategy =
        new LoggingDecorator(
            new ProcessingOrderStrategy()
        );

    strategy.execute();

        context.setState(new DeliveredState());
    }
}