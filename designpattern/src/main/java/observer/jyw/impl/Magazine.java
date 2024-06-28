package observer.jyw.impl;

import com.google.common.collect.Lists;
import observer.jyw.Observer;
import observer.jyw.Subject;

import java.util.List;

public class Magazine implements Subject {

    private List<Observer> observers;

    //主题中的一些状态信息，当这些信息改变的时候，需要通知观察者
    private Object state;

    public Magazine(){
        observers = Lists.newArrayList();
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    public Object getState() {
        return state;
    }

    //当subject的state变化的时候，即意味着需要通知观察者了
    public void setState(Object state) {
        this.state = state;
        notifyObservers();
    }

    @Override
    public void registerObservers(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer->observer.update(this.getState()));
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

}
