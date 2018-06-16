package top.imcw.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

public class StopHandler implements ApplicationListener<ContextStoppedEvent> {
    @Override
    public void onApplicationEvent(ContextStoppedEvent contextStoppedEvent) {
        System.out.println("context stopped");
    }
}
