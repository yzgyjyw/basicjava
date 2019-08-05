package observer.jyw.impl;


import observer.jyw.Observer;

public class ObserverB implements Observer {
    @Override public void update(Object o) {
        System.out.println(this.getClass().getName()+"获取到来自主题的消息改变"+o);
        //处理获取到的消息
    }
}
