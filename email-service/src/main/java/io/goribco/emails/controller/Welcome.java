package io.goribco.emails.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Welcome {
    @GetMapping("/")
    public String home() {
        return "Hello from Home of Top Profile. Random Id: ";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello by SPB2 %s!", name);
    }

    @GetMapping("/mongo")
    public String mongo(@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {

        return String.format("Hello by SPB2 %s!", name);
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {

        return "API Server Detail 420 Hello Paike";
    }
}
