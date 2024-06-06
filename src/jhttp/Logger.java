package src.jhttp;

public class Logger {
    private static final Logger instance = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void Print(String prefix, String format, Object ...args) {
        String f = String.format("[%s]: %s%n", prefix, format);
        System.err.printf(f, args);
    }
     
    public void Info(String format, Object ...args) {
        this.Print("INFO", format, args);
    }

    public void Error(String format, Object ...args) {
        this.Print("ERROR", format, args);
    }
    
    public void Fatal(String format, Object ...args) {
        this.Print("FATAL", format, args);
        System.exit(1);
    }
}
