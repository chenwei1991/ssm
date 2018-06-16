package top.imcw.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;

public class CustomEventHandler implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent customEvent) {
        System.out.println(customEvent.getSource().getClass().getName());
    }
}

//class Publisher implements ApplicationEventPublisher{
//
//    @Override
//    public void publishEvent(ApplicationEvent event) {
//
//    }
//
//    @Override
//    public void publishEvent(Object o) {
//
//    }
//}