package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/* The rest controller not only makes this class a component but also makes sure that
all the end points we define here will returnn the response in JSON format */

@RestController


public class HealthCheck {
    @GetMapping("/HealthCheck")

    public String healthCheck(){
        return "OK";
    }
}
