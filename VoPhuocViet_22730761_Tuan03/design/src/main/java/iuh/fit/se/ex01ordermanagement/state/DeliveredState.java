package iuh.fit.se.ex01ordermanagement.state;

import iuh.fit.se.ex01ordermanagement.context.OrderContext;
import iuh.fit.se.ex01ordermanagement.decorator.LoggingDecorator;
import iuh.fit.se.ex01ordermanagement.strategy.DeliveredOrderStrategy;
import iuh.fit.se.ex01ordermanagement.strategy.OrderStrategy;

public class DeliveredState implements OrderState {

    @Override
    public void handle(OrderContext context) {

    OrderStrategy strategy =
        new LoggingDecorator(
            new DeliveredOrderStrategy()
        );

    strategy.execute();
    }
}