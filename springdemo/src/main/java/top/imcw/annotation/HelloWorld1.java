package top.imcw.annotation;

import org.springframework.stereotype.Component;

@Component
public class HelloWorld1 {
    private String name;

    public String getName() {
        return "hello world";
    }
}
