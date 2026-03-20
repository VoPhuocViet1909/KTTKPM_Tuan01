package part2.problem1_state;

// Main Demo
public class OrderStateDemo {
    public static void main(String[] args) {
        System.out.println("=== Demo State Pattern (Quản lý đơn hàng) ===");
        
        OrderContext order = new OrderContext();
        
        // Quy trình bình thường
        order.process(); // Mới tạo -> Đang xử lý
        order.process(); // Đang xử lý -> Đã giao
        order.process(); // Đã giao -> Kết thúc

        System.out.println("\n--- Trường hợp hủy đơn ---");
        OrderContext order2 = new OrderContext();
        order2.process(); // Mới tạo -> Đang xử lý
        // Giả sử có sự kiện hủy xảy ra
        System.out.println("! Khách hàng yêu cầu hủy đơn !");
        order2.setState(new CancelledState());
        order2.process(); // Xử lý trạng thái hủy
    }
}
        order2.process(); // Xử lý trạng thái hủy
    }
}
