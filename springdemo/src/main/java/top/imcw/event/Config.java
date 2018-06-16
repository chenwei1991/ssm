package top.imcw.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public HelloWorld getHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setMessage("你好，世界");
        return helloWorld;
    }

    @Bean
    public StartHandler getStarHandler() {
        return new StartHandler();
    }

    @Bean
    public StopHandler getStopHandler() {
        return new StopHandler();
    }


    @Bean
    public CustomPublisher getCustomPublisher() {
        return new CustomPublisher(); // 会自动调用setApplicationEventPublisher把当前ApplicationContext传入
    }

    @Bean
    public CustomEventHandler getCustomEventHandler() {
        return new CustomEventHandler();
    }

    @Bean
    public CustomPublisher1 getCustomPublisher1() {
        return new CustomPublisher1();
    }
}
