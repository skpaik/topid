package io.goribco.bgservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/background-service/email")
public class EmailSendController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSendController.class);

    @GetMapping("/send/{email}/{event}")
    public List<String> sendEmail(@PathVariable("email") String email, @PathVariable("event") int event) {
        LOGGER.info("sendEmail={}, event={}", email, event);

        return List.of();
    }
}
