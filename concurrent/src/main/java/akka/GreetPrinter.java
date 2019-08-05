package akka;

import akka.actor.UntypedActor;

public class GreetPrinter extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Greeting){
            System.out.println(((Greeting) message).message);
        }
    }
}
