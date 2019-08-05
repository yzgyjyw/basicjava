package facade;

public class Facade {


    ServiceA serviceA;
    ServiceB serviceB;

    public Facade() {
        serviceA = new ServiceA();
        serviceB = new ServiceB();
    }

    public void method1(){
        serviceA.method1();
    }

    public void method2(){
        serviceB.method2();
    }
}
