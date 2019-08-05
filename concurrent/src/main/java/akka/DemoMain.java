package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DemoMain {

    public static void main(String[] args) throws TimeoutException {
        //创建一个actor系统
        final ActorSystem system = ActorSystem.create("helloakka");
        //在actor系统内创建一个actor,为所创建的actor返回一个引用
        final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");

        ActorRef actorBasedLambda = system.actorOf(Props.create(ActorBasedLambda.class), "actorBasedLambda");
        actorBasedLambda.tell(1,ActorRef.noSender());

//        final Inbox inbox = Inbox.create(system);

        //向greeter发送一个WhoToGreet消息
        greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());

        /*inbox.send(greeter, new Greet());

        Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Greeting:" + greeting1.message);
*/
    }

}
