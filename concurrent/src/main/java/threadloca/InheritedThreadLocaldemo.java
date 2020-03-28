package threadloca;

// InheritableThreadLocal继承自ThreadLocal
public class InheritedThreadLocaldemo {
    // 普通的thread变量不支持子线程继承父线程中的变量，观察原理也可以知道，ThreadLocalMap是以线程为单位的
//    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    //InheritableThreadLcoal的原理：在创建子线程时，会将父线程中的inheritableThreadLocals中的Entry拷贝到子线程的inheritableThreadLocals中
    // 详细请见Thread类的init方法
    /**
     * if (inheritThreadLocals && parent.inheritableThreadLocals != null)
     *             this.inheritableThreadLocals =
     *                 ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
     */
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        threadLocal.set("main thread value");
        Thread threadOne = new Thread(() -> {
            System.out.println("childThread\t" + threadLocal.get());
        });

        threadOne.start();

    }
}


