package springdemo;


import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public String getName() {
        return "name123";
    }

}
