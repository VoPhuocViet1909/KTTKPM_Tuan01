package part2.problem3_decorator;

public abstract class PaymentDecorator implements PaymentMethod {
    protected PaymentMethod wrappedPayment;

    public PaymentDecorator(PaymentMethod payment) {
        this.wrappedPayment = payment;
    }

    @Override
    public void pay(double amount) {
        wrappedPayment.pay(amount);
    }
}
