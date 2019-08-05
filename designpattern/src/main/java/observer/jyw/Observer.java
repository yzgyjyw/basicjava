package observer.jyw;

public interface Observer {
    //这里的参数是用来记录subject的一些特性，以供observer使用，有时可以直接传入subject自己
    void update(Object o);
}
