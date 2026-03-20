package part1;


// Main Class để chạy demo
public class LoggerDemo {
    public static void main(String[] args) {
        System.out.println("=== Demo Singleton & Factory Pattern ===");
        
        // Lấy instance duy nhất của Factory
        LoggerFactory factory = LoggerFactory.getInstance();
        
        // Tạo các logger khác nhau
        Logger console = factory.createLogger(LoggerType.CONSOLE);
        Logger file = factory.createLogger(LoggerType.FILE);
        Logger db = factory.createLogger(LoggerType.DATABASE);

        // Sử dụng
        console.log("Hệ thống bắt đầu khởi động.");
        file.log("Lỗi kết nối mạng tại dòng 45.");
        db.log("Người dùng Admin đăng nhập.");
    }
}
