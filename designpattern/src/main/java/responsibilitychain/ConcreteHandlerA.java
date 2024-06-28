package responsibilitychain;

public class ConcreteHandlerA extends AbstractHandler {
    @Override
    public void action(int num) {
        if (num > 3) {
            if (getNext() != null) {
                getNext().action(num);
            } else {
                System.out.println("Nobody handle it");
            }
        } else {
            System.out.println("ConcreteHandlerA handle it");
        }
    }
}