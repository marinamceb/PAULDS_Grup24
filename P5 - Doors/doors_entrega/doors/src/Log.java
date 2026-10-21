import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Logs every entered code with door id, date and time and door state,
// and always passes the code to the next handler.
public class Log extends CodeHandler {
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log(CodeHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleCode(String code, Door door) {
        System.out.println("   handle Log");
        String state = door.isLocked() ? "locked" : "unlocked";
        System.out.println("   [LOG] door " + door.getId()
                + ", code " + code
                + ", state " + state
                + ", at " + LocalDateTime.now().format(FORMAT));
        super.handleCode(code, door);
    }
}
