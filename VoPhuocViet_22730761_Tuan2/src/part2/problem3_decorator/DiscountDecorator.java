package part2.problem3_decorator;

public class DiscountDecorator extends PaymentDecorator {
    private double discountAmount;

    public DiscountDecorator(PaymentMethod payment, double discountAmount) {
        super(payment);
        this.discountAmount = discountAmount;
    }

    @Override
    public void pay(double amount) {
        double total = amount - discountAmount;
        if (total < 0) total = 0;
        System.out.printf(">> [Giảm giá] Áp dụng mã giảm %.2f.\n", discountAmount);
        super.pay(total);
    }
}
