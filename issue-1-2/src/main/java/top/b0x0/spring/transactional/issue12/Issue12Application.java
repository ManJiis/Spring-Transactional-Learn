package top.b0x0.spring.transactional.issue12;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ManJiis
 */
@SpringBootApplication
@MapperScan("top.b0x0.spring.transactional.common.mapper")
//@EnableAspectJAutoProxy(exposeProxy = true)
//@EnableTransactionManagement
public class Issue12Application {

    public static void main(String[] args) {
        SpringApplication.run(Issue12Application.class, args);
    }

}