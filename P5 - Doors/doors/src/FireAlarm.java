// If the door is not locked and the fire alarm code is entered,
// opens the door and fires an alarm at the surveillance station.
// Otherwise passes the code to the next handler.
public class FireAlarm extends CodeHandler {
    private String codeFireAlarm;

    public FireAlarm(String code, CodeHandler nextHandler) {
        super(nextHandler);
        codeFireAlarm = code;
    }

    @Override
    public void handleCode(String code, Door door) {
        System.out.println("   handle FireAlarm");
        if (!door.isLocked() && codeFireAlarm.equals(code)) {
            door.resetState();
            // would send a message to the surveillance station
            System.out.println("   >>> FIRE ALARM sent to surveillance station from door "
                    + door.getId());
            door.open();
            // request served, chain stops here
        } else {
            super.handleCode(code, door);
        }
    }
}
