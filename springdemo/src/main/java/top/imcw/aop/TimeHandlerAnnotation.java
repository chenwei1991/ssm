package top.imcw.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TimeHandlerAnnotation {

    @Pointcut("execution(* top.imcw.aop.HelloWorld.do*(..))")
    private void select() {
    }

    @Around("select()")
    public void printTime(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("currentTimeMillis:" + System.currentTimeMillis());
        pjp.proceed();
        System.out.println("currentTimeMillis:" + System.currentTimeMillis());
    }
}
