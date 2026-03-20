package part1;

public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        // Mô phỏng ghi ra file
        System.out.println("[File] Write to disk: " + message);
    }
}
