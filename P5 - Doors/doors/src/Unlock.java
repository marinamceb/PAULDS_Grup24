// If the door is locked and the unlock code is entered, unlocks it.
// Otherwise passes the code to the next handler.
public class Unlock extends CodeHandler {
    private String codeUnlock;

    public Unlock(String code, CodeHandler nextHandler) {
        super(nextHandler);
        codeUnlock = code;
    }

    @Override
    public void handleCode(String code, Door door) {
        System.out.println("   handle Unlock");
        if (door.isLocked() && codeUnlock.equals(code)) {
            door.resetState();
            System.out.println("   >>> door " + door.getId() + " unlocked");
            // request served, chain stops here
        } else {
            super.handleCode(code, door);
        }
    }
}
