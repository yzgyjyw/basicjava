import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/*disruptor的基本使用：
    扩展点：   1   单生产者还是多生产者
              2   消费者等待策略
    注意点：   采用ringBuffer.publish的时候，一定要放在finally块中，确保一定会发布
*/
public class DisruptorTest {
    public static void main(String[] args) {
        publishByMethod2();
    }



    static void publishByMentod1(){
        Disruptor<LongEvent> disruptor = new Disruptor<>(new EventFactory<LongEvent>() {
            @Override
            public LongEvent newInstance() {
                return new LongEvent();
            }
        }, 8, Executors.newFixedThreadPool(8), ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith(new EventHandler<LongEvent>() {
            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println(event.getValue()+"\t"+sequence+"\t"+endOfBatch);
            }
        });
        disruptor.start();

        //发布事件 方式一
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        while(true){
            //请求下一个事件序号
            long next = ringBuffer.next();
            try{
                //获取该序号对应的事件对象，直接修改该对象的值，而不是删除再新建，防止频繁GC
                LongEvent longEvent = ringBuffer.get(next);
                longEvent.setValue(1000);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ringBuffer.publish(next);
            }
        }
    }

    static void publishByMethod2(){
        Disruptor<LongEvent> disruptor = new Disruptor<>(new EventFactory<LongEvent>() {
            @Override
            public LongEvent newInstance() {
                return new LongEvent();
            }
        }, 8, Executors.newFixedThreadPool(8), ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith(new EventHandler<LongEvent>() {
            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println(event.getValue()+"\t"+sequence+"\t"+endOfBatch);
            }
        });
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long data = 1000;
        /*ringBuffer.publishEvent(new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long data) {
                event.setValue(data);
            }
        },data);*/
        Translator translator = new Translator();
        while(true){
            ringBuffer.publishEvent(translator,data);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Translator implements EventTranslatorOneArg<LongEvent,Long>{

    @Override
    public void translateTo(LongEvent event, long sequence, Long data) {
        event.setValue(data);
    }
}


class LongEvent{
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
