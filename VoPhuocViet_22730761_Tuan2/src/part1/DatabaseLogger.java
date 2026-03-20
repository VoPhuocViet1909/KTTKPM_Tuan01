package part1;

public class DatabaseLogger implements Logger {
    @Override
    public void log(String message) {
        // Mô phỏng ghi vào DB
        System.out.println("[Database] Insert into logs table: " + message);
    }
}
