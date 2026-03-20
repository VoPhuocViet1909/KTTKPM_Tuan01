package part2.problem3_decorator;

public class ProcessingFeeDecorator extends PaymentDecorator {
    private double fee;

    public ProcessingFeeDecorator(PaymentMethod payment, double fee) {
        super(payment);
        this.fee = fee;
    }

    @Override
    public void pay(double amount) {
        double total = amount + fee;
        System.out.printf(">> [Phí xử lý] Cộng thêm %.2f phí.\n", fee);
        super.pay(total);
    }
}
