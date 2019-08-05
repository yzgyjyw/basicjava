package observer.jdk;


import java.util.Observable;
import java.util.Observer;

/**
 * Observr jdk提供的观察者模式中的观察者对象
 */
public class ObserverC implements Observer {
    /**
     *
     * @param o 使用jdk的实现，subject会在通知observer时，将自己作为第一个参数传递过来
     * @param arg subject#notifyObservers(arg)，传递过来的，这里就分是push还是pull形式的
     */
    @Override public void update(Observable o, Object arg) {
        if(o instanceof Magazine){
            System.out.println("来自于主题Magazine的状态改变通知"+((Magazine) o).getState());
        }
    }
}
