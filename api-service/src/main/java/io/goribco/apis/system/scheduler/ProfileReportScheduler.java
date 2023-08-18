package io.goribco.apis.system.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ProfileReportScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileReportScheduler.class);

    @Scheduled(fixedRate = 300000L)
    public void checkUserReport() {
        LOGGER.info("ProfileReportScheduler Job running in " + new Date());
    }
}
