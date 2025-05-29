package hello.spring.webapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * スターターを使用したWebアプリケーションのメインクラス
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"hello.spring.webapp1", "hello.spring.logger"})
public class Webapp1Application {

    public static void main(String[] args) {
        SpringApplication.run(Webapp1Application.class, args);
    }
}
