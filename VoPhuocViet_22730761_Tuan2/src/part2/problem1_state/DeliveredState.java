package part2.problem1_state;

public class DeliveredState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("- Trạng thái: Đã giao. Cập nhật thành công lên hệ thống.");
        // Đây là trạng thái cuối, không chuyển đi đâu nữa hoặc có thể chuyển sang trạng thái "Hoàn tất"
    }
}
