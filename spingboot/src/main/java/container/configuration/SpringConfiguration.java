package container.configuration;

import container.bean.JavaBean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

@Configuration
@ComponentScan(basePackages = {"container"})
public class SpringConfiguration {

    @Bean(initMethod = "steup",destroyMethod = "destory")
    public JavaBean1 getJavaBean1() {
        JavaBean1 javaBean1 = new JavaBean1();
        javaBean1.setName("name");
        return javaBean1;
    }

}
