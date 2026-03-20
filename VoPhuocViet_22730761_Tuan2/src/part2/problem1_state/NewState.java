package part2.problem1_state;

public class NewState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("- Trạng thái: Mới tạo. Đang kiểm tra thông tin...");
        // Logic kiểm tra...
        System.out.println("  -> Thông tin hợp lệ.");
        // Chuyển sang trạng thái tiếp theo
        context.setState(new ProcessingState());
    }
}
