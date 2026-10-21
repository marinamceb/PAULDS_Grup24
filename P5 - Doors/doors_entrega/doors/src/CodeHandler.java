// Abstract handler of the Chain of Responsibility.
// Keeps the reference to the next handler and implements the common
// behaviour "pass the request to the next one", so that concrete
// handlers just call super.handleCode(...) (Don't Repeat Yourself).
public abstract class CodeHandler {
    protected CodeHandler nextHandler;

    public CodeHandler(CodeHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleCode(String code, Door door) {
        if (nextHandler != null) {
            nextHandler.handleCode(code, door);
        } else {
            System.out.println("   reached end of chain");
        }
    }
}
