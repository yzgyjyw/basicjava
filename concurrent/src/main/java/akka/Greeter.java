package akka;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {

    String greeting = "";

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof WhoToGreet) {
            System.out.println("receive WhoToGreet\t" + ((WhoToGreet) message).who);
            greeting = "hello," + ((WhoToGreet) message).who;
        } else if (message instanceof Greet) {
            System.out.println("receive Greet\t" + message.toString());
            getSender().tell(new Greeting(greeting), getSelf());
        }
    }
}
