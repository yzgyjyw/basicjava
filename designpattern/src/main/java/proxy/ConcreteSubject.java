package proxy;

public class ConcreteSubject implements ISubject {
    @Override
    public void operation() {
        System.out.println("ConcreteSubject");
    }
}
