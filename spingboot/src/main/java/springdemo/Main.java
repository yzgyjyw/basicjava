package springdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);

        Object getName = context.getBean("getName");

        System.out.println(getName);

    }

}
