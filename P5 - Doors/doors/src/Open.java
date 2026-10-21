// If the door is not locked and the open code is entered, opens it.
// Otherwise passes the code to the next handler.
public class Open extends CodeHandler {
    private String codeOpen;

    public Open(String code, CodeHandler nextHandler) {
        super(nextHandler);
        codeOpen = code;
    }

    @Override
    public void handleCode(String code, Door door) {
        System.out.println("   handle Open");
        if (!door.isLocked() && codeOpen.equals(code)) {
            door.resetState();
            door.open();
            // request served, chain stops here
        } else {
            super.handleCode(code, door);
        }
    }
}
