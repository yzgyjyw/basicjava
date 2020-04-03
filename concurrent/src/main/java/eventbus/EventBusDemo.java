package eventbus;


import com.google.common.base.Joiner;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(EventBusDemo.class);

    public static void main(String[] args) {
        EventBus eventBus = new EventBus(((exception, context) -> {
            LOGGER.error("context : {}, exception : {}", context, exception);
        }));
        eventBus.register(new EventBusDemo());
        eventBus.post("abc");
        eventBus.post(1L);
    }

    @Subscribe
    public void doActionString1(String event) {
        LOGGER.info("do Action String1 : {}", event);
    }

    @Subscribe
    public void doActionString2(String event) {
        throw new RuntimeException(event);
    }

    @Subscribe
    public void doActionInt(Integer a) {
        LOGGER.info("do Action int : {}", a);
    }

    @Subscribe
    public void doActionDeadEvent(DeadEvent event) {
        LOGGER.info("do Action DeadEvent : {}", event);
    }


}
