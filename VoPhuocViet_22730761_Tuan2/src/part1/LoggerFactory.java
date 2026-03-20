package part1;

// 4. LoggerFactory kết hợp Singleton
// Singleton: Đảm bảo chỉ có 1 Factory quản lý việc tạo Logger
// Factory: Che giấu logic tạo đối tượng phức tạp
public class LoggerFactory {
    private static LoggerFactory instance;

    // Private constructor (Singleton)
    private LoggerFactory() {}

    // Global access point (Singleton)
    public static synchronized LoggerFactory getInstance() {
        if (instance == null) {
            instance = new LoggerFactory();
        }
        return instance;
    }

    // Factory Method
    public Logger createLogger(LoggerType type) {
        switch (type) {
            case CONSOLE:
                return new ConsoleLogger();
            case FILE:
                return new FileLogger();
            case DATABASE:
                return new DatabaseLogger();
            default:
                throw new IllegalArgumentException("Loại Logger không hỗ trợ!");
        }
    }
}
