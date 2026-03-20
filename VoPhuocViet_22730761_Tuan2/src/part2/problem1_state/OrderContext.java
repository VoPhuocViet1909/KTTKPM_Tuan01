package part2.problem1_state;

public class OrderContext {
    private OrderState currentState;

    public OrderContext() {
        // Trạng thái mặc định ban đầu
        this.currentState = new NewState();
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public void process() {
        this.currentState.handle(this);
    }
}
