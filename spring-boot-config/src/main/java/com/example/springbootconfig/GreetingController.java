package com.example.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class GreetingController {

    @Autowired
    private DBSettings dbSettings;

    @Value("${my.greeting: default greeting}")
    private String greetingMessage;

    @Value("${my.list}")
    private List<String> myList;

    @Value("#{${my.map}}")
    private Map<String, String> myMap;

    @GetMapping("/greeting")
    public String getGreeting() {
        return greetingMessage;
    }

    @GetMapping("/dbsettings")
    public String getDbSettings() {
        return dbSettings.getConnection() + dbSettings.getPassword() + dbSettings.getUser();
    }
}
