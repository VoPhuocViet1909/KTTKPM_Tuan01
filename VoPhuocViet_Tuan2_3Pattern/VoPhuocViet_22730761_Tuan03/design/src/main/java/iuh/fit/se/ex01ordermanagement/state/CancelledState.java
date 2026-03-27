package iuh.fit.se.ex01ordermanagement.state;

import iuh.fit.se.ex01ordermanagement.context.OrderContext;
import iuh.fit.se.ex01ordermanagement.decorator.LoggingDecorator;
import iuh.fit.se.ex01ordermanagement.decorator.ValidationDecorator;
import iuh.fit.se.ex01ordermanagement.strategy.CancelledOrderStrategy;
import iuh.fit.se.ex01ordermanagement.strategy.OrderStrategy;

public class CancelledState implements OrderState {

    @Override
    public void handle(OrderContext context) {

    OrderStrategy strategy =
        new LoggingDecorator(
            new ValidationDecorator(
                new CancelledOrderStrategy()
            )
        );

    strategy.execute();
    }
}