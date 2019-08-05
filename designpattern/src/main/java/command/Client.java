package command;

public class Client {

    //命令的传播方向：invoker(发送者)---->Command---->Receiver(命令得到接收者处理命令)
    /*举例：https://www.cnblogs.com/java-my-life/archive/2012/06/01/2526972.html
    * MP3播放器
    *
    * */
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        //通常情况下有不止一个command
        Command command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker(command);
        invoker.action();
    }
}
