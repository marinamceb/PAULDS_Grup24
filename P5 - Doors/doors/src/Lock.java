// Counts failed attempts and locks the door after MaxNumTrials of them.
//
// Design change with respect to the slides: counting failed trials is done
// here and not in Open. A code only reaches this handler when no previous
// handler (Unlock, FireAlarm, Open) has served it, that is, when it is a
// failed attempt. This way a wrong code is counted exactly once even if
// both Open and FireAlarm are in the chain, and doors without Lock don't
// count anything. Consequence: Lock must be the last handler of the chain.
public class Lock extends CodeHandler {
    private static final int MaxNumTrials = 3;

    public Lock(CodeHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleCode(String code, Door door) {
        System.out.println("   handle Lock");
        if (door.isLocked()) {
            System.out.println("   door " + door.getId()
                    + " is locked, invalid unlock code, nothing done");
        } else {
            door.incrementNumTrials();
            System.out.println("   failed trial " + door.getNumTrials()
                    + " of " + MaxNumTrials);
            if (door.getNumTrials() >= MaxNumTrials) {
                door.lock();
                System.out.println("   >>> door " + door.getId() + " LOCKED");
            }
        }
        super.handleCode(code, door);
    }
}
