package top.imcw.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("top.imcw.annotation")
public class HelloWorldConfiguration {
    @Bean("hello")
    public HelloWorld getHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("Hello World!");
        return helloWorld;
    }
}
