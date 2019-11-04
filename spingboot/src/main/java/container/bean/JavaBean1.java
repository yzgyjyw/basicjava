package container.bean;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 先调用构造方法
// 然后@PostConstruct修饰的方法
// init-method
// @PreDestory修饰的方法
// destory-method
public class JavaBean1 {

    @Required
    //@Required 注释应用于 bean 属性的 setter 方法，它表明受影响的 bean 属性在配置时必须指定初始值，否则容器就会抛出一个 BeanInitializationException 异常
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void printClassName() {
        System.out.println(this.getClass().getName());
    }

    public JavaBean1() {
        System.out.println(this.getClass().getName() + "\tconstruct");
    }


    @PostConstruct
    public void postConstruct() {
        System.out.println(this.getClass().getName() + "\tpost construct");
    }


    @PreDestroy
    public void preDestroy() {
        System.out.println(this.getClass().getName() + "\tpre destroy");
    }

    private void steup() {
        System.out.println(this.getClass().getName() + "\tsetup");
    }

    private void destory() {
        System.out.println(this.getClass().getName() + "\tdestory");
    }
}
