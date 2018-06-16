package top.imcw.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class TimeHandler {
    public void printTime(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("currentTimeMillis:" + System.currentTimeMillis());
        pjp.proceed();
        System.out.println("currentTimeMillis:" + System.currentTimeMillis());
    }
}
