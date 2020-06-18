package responsibilitychain;

public abstract class AbstractHandler implements IHandler {

    private IHandler next;

    public void setNext(IHandler handler) {
        next = handler;
    }

    public IHandler getNext() {
        return next;
    }
}
