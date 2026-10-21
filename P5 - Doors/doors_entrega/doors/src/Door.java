// A door no longer knows its codes nor what to do with each code:
// it just has a chain of handlers and keeps its own state
// (locked, number of failed trials).
public class Door {
    private String id;
    private CodeHandler codeHandler;
    private int numTrials = 0;
    private boolean locked = false;

    public Door(String ident, CodeHandler ch) {
        id = ident;
        codeHandler = ch;
    }

    // the request of the chain of responsibility: process the entered code
    public void processCode(String code) {
        System.out.println("door " + id + " <- code " + code);
        codeHandler.handleCode(code, this);
    }

    public void resetState() {
        numTrials = 0;
        locked = false;
    }

    public void incrementNumTrials() { numTrials++; }
    public int getNumTrials() { return numTrials; }
    public boolean isLocked() { return locked; }
    public void lock() { locked = true; }

    public void open() {
        // would send a pulse to the door's electromagnet
        System.out.println("   >>> door " + id + " opened");
    }

    public String getId() { return id; }

    // change the behaviour of the door at execution time
    public void setCodeHandler(CodeHandler ch) {
        codeHandler = ch;
        resetState();
    }
}
