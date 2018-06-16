package top.imcw.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy  // 对应配置文件中的<aop:aspectj-autoproxy />
@Configuration
public class Config {
    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }

    @Bean
    public LogHandlerAnnotation logHandlerAnnotation() {
        return new LogHandlerAnnotation();
    }

    @Bean
    public TimeHandlerAnnotation timeHandlerAnnotation() {
        return new TimeHandlerAnnotation();
    }
}
