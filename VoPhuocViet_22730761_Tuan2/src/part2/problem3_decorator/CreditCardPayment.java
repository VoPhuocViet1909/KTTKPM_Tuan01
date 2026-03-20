package part2.problem3_decorator;

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.printf("Thanh toán %.2f bằng Thẻ tín dụng.\n", amount);
    }
}
