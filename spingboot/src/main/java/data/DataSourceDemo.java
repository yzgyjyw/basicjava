package data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
@Slf4j
public class DataSourceDemo implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceDemo.class);
    }


    /**
     * 关于Spring boot集成DataSource
     * <p>
     * SpingBoot 通过 DataSourceAutoConfiguration配置了DataSource
     * <p>
     * 通过DataSourceTransactionManagerAutoConfiguration配置了DataSourceTransactionManager
     * <p>
     * 通过JdbcTemplateAutoConfiguration配置了JdbcTemplate
     * <p>
     * 所有这些符合条件时才会进行配置(例如如果我们自己已经配置DataSource的bean,那么SpringBoot就不会帮助我们创建了)
     * <p>
     * <p>
     * 数据源相关配置属性
     * <p>
     * spring.datasource.url=jdbc:mysql://localhost/test
     * spring.datasource.username=user
     * spring.datasource.password=pwd
     * spring.datasource.deiver-class-name=com.mysql.jdbc.Driver(可选)
     */

    /**
     * 配置多数据源
     * 1. 不同数据源的配置需要分开
     * 2. 关注每次使用的数据源
     * 有多个DataSource时系统如何判断选择
     * 对应的设施(事物,ORM等)如何选择DataSource
     */

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("datasource:" + dataSource);
        Connection connection = dataSource.getConnection();
        System.out.println("connection:" + connection);
        connection.close();

        jdbcTemplate.queryForList("select * from Persons").forEach(result -> System.out.println(result));
    }
}
