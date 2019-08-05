package decorator;

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();

        ConcreteDecoratorA concreteDecoratorA = new ConcreteDecoratorA(component);
        concreteDecoratorA.sampleOperation();

        ConcreteDecoratorB concreteDecoratorB = new ConcreteDecoratorB(component);
        concreteDecoratorB.sampleOperation();
    }
}