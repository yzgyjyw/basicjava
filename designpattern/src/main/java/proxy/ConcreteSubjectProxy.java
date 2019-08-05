package proxy;

public class ConcreteSubjectProxy implements ISubject {

    ISubject subject;

    public ConcreteSubjectProxy() {
        this.subject = new ConcreteSubject();
    }

    @Override
    public void operation() {
        System.out.println("代理执行之前");
        subject.operation();
        System.out.println("代理执行之后");
    }

}
