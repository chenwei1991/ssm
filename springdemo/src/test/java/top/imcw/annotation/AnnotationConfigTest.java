package top.imcw.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationConfigTest {

    /**
     * 配置文件开启注解，@PostConstruct，@PreDestroy，init-method，destroy-method顺序
     */
    @Test
    public void test() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("annotation/Beans.xml");
        HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
        System.out.println(helloWorld.getName());
        context.registerShutdownHook();

    }

    /**
     * 纯注解配置
     */
    @Test
    public void test1() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class); //当指定类创建ApplicationContext时，不需要注解@Cnofiguration
        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        System.out.println(helloWorld.getName());
    }

    /**
     * @ComponentScan，@Component
     */
    @Test
    public void test2() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        HelloWorld1 helloWorld = ctx.getBean(HelloWorld1.class);
        System.out.println(helloWorld.getName());
    }

}
