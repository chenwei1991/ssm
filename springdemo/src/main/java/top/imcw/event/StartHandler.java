package top.imcw.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class StartHandler implements ApplicationListener<ContextStartedEvent> {
    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        System.out.println("context started");
    }
}
