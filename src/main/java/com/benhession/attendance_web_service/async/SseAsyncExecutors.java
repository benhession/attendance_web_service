package com.benhession.attendance_web_service.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Timer;
import java.util.TimerTask;


@Component
public class SseAsyncExecutors {

    Logger logger = LoggerFactory.getLogger("SSE");

    @Async
    public void pingSseEmitter(SseEmitter emitter, String name) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    emitter.send("ping");
                } catch (Exception e) {
                    logger.debug("Error sending ping to: ".concat(name));
                    emitter.complete();
                    this.cancel();
                }
            }
        }, 10000, 30000);
    }
}
