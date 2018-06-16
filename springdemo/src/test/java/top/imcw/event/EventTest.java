package top.imcw.event;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventTest {
    /**
     * ContextStartedEvent、ContextStoppedEvent事件监听
     */
    @Test
    public void test() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.start();
        HelloWorld helloWorld = context.getBean(HelloWorld.class);
        System.out.println(helloWorld.getMessage());
        context.stop();
    }

    /**
     * 自定义事件，自定义发布者（继承ApplicationEventPublisherAware）
     */
    @Test
    public void test1() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        CustomPublisher publisher = context.getBean(CustomPublisher.class);
        publisher.publish("aaa");
        System.out.println(publisher.getApplicationEventPublisher() == context);
    }

    /**
     * 使用ApplicationContext充当发布者
     */
    @Test
    public void test2() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.publishEvent(new CustomEvent("aaa"));
    }

    /**
     * 自定义发布者（继承ApplicationContextAware）
     */
    @Test
    public void test3() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        CustomPublisher1 publisher = context.getBean(CustomPublisher1.class);
        publisher.publish("aaa");
    }
}
