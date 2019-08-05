package observer.jdk;

import java.util.Observable;

/**
 * Observable jdk提供的观察者模式中的主题对象
 */
public class Magazine extends Observable{
    private Object state;

    public Object getState() {
        return state;
    }

    //使用JDK提供的Observable，在某些变量改变的时候，需要setChanged和notifyObservers，比较灵活
    public void setState(Object state) {
        this.state = state;

        //当状态改变的时候，需要通知观察者的时候必须setChanged()方法
        setChanged();

        //没有参数，表示的是由observer主动来拉去数据
        notifyObservers();
        //有参数，表示是由subject推送数据
        //notifyObservers(state);
    }


}
