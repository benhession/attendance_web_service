package com.benhession.attendance_web_service.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

@Component
public class SseAsyncExecutors {

    Logger logger = Logger.getLogger("SseAsyncExecutors");

    @Async
    public void pingSseEmitter(SseEmitter emitter, String name) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    emitter.send("ping");
                } catch (Exception e) {
                    logger.warning("Error sending ping to: ".concat(name));
                    emitter.complete();
                    this.cancel();
                }
            }
        }, 10000, 30000);
    }
}
