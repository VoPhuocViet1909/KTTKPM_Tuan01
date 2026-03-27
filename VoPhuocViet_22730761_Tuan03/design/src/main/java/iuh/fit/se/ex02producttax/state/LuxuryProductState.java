package iuh.fit.se.ex02producttax.state;

import iuh.fit.se.ex02producttax.context.TaxContext;
import iuh.fit.se.ex02producttax.decorator.LoggingTaxDecorator;
import iuh.fit.se.ex02producttax.decorator.ValidationTaxDecorator;
import iuh.fit.se.ex02producttax.model.Product;
import iuh.fit.se.ex02producttax.strategy.*;

import java.util.List;

public class LuxuryProductState implements TaxState {
    @Override
    public void handle(TaxContext context) {
        Product product = context.getProduct();

        TaxStrategy strategy = new LoggingTaxDecorator(
                new ValidationTaxDecorator(
                        new CompositeTaxStrategy(List.of(
                                new VatTaxStrategy(),
                                new ExciseTaxStrategy(),
                                new LuxuryTaxStrategy()
                        ))
                )
        );

        double tax = strategy.calculate(product.getBasePrice());
        double total = product.getBasePrice() + tax;

        System.out.printf("[LUXURY] %s | Gia goc: %,.0f | Thue: %,.0f | Tong: %,.0f%n",
                product.getName(), product.getBasePrice(), tax, total);
    }
}
