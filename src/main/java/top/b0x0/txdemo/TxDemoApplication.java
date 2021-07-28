package top.b0x0.txdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ManJiis
 */
@SpringBootApplication
@MapperScan("top.b0x0.txdemo.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
public class TxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxDemoApplication.class, args);
    }

}
