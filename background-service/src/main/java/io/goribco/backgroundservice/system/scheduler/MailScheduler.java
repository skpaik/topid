package io.goribco.backgroundservice.system.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MailScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailScheduler.class);
    @Scheduled(fixedRate = 200000L)
    public void sendNewsLetter() {
        LOGGER.info("MailScheduler Job running in " + new Date());
    }
}
