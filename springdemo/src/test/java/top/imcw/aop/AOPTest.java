package top.imcw.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AOP除了依赖spring-aop包以外，还依赖aopalliance.jar、aspectjweaver.jar
 */
public class AOPTest {
    /**
     * 配置文件版
     */
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop/Beans.xml");
        HelloWorld helloWorld = context.getBean(HelloWorld.class);
        helloWorld.printHelloWorld();
        helloWorld.doPrint();
    }

    /**
     * 注解版，@EnableAspectJAutoProxy
     */
    @Test
    public void test2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        HelloWorld helloWorld = context.getBean(HelloWorld.class);
        helloWorld.printHelloWorld();
        helloWorld.doPrint();
    }
}
