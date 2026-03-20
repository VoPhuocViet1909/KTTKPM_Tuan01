package part2.problem3_decorator;

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.printf("Thanh toán %.2f bằng PayPal.\n", amount);
    }
}
