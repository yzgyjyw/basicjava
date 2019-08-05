package proxy;

/**
 * 代理与装饰的区别：
 *
 *     从语意上讲，代理模式是为控制对被代理对象的访问，而装饰模式是为了增加被装饰对象的功能
 *     代理类所能代理的类完全由代理类确定，装饰类装饰的对象需要根据实际使用时客户端的组合来确定
 *     被代理对象由代理对象创建，客户端甚至不需要知道被代理类的存在；被装饰对象由客户端创建并传给装饰对象
 */
public class Client {

    public static void main(String[] args) {
        ISubject proxy = new ConcreteSubjectProxy();
        proxy.operation();
    }

}
