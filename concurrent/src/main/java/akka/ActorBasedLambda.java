package akka;

import akka.actor.AbstractActor;

public class ActorBasedLambda extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,s->{
            System.out.println(s);
        }).match(Integer.class,i->{
            return true;}, i->{
                    System.out.println(i);
                }).build();

    }
}
