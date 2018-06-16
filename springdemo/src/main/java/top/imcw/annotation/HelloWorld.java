package top.imcw.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class HelloWorld {
    private String  name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(){
        System.out.println("xml init");
    }

    @PostConstruct
    public void init1(){
        System.out.println("anno init");
    }

    public void destroy(){
        System.out.println("xml destroy");
    }

    @PreDestroy
    public void destroy1(){
        System.out.println("anno destroy");
    }
}
