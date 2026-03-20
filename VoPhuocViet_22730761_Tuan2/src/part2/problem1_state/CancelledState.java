package part2.problem1_state;

public class CancelledState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("- Trạng thái: Hủy. Đang tiến hành hoàn tiền cho khách.");
    }
}
