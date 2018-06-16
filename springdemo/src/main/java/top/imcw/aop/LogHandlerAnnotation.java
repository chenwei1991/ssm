package top.imcw.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogHandlerAnnotation {

    @Pointcut("execution(* top.imcw.aop.HelloWorld.print*(..))")
    private void select(){}

    @Before("select()")
    public void logBefore() {
        System.out.println("log before");
    }

    @After("select()")
    public void logAfter() {
        System.out.println("log after");
    }
}
