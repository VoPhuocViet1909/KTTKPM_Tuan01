package part2.problem1_state;

public class ProcessingState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("- Trạng thái: Đang xử lý. Đang đóng gói và vận chuyển...");
        // Logic vận chuyển...
        // Chuyển sang trạng thái tiếp theo
        context.setState(new DeliveredState());
    }
}
