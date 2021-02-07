package container;

import container.bean.JavaBean1;
import container.bean.JavaBean2;
import container.configuration.SpringConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @author jyw
 */
public class ContainerTest {

    public static void main(String[] args) {



        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        JavaBean1 javaBean1 = context.getBean(JavaBean1.class);
        JavaBean2 javaBean2 = context.getBean(JavaBean2.class);
        javaBean1.printClassName();
        context.close();
        System.out.println(1);



    }

}
