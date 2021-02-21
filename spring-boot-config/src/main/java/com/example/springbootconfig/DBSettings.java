package com.example.springbootconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration // Make this class a bean
@ConfigurationProperties("db") // Look up configuration properties starting with "db."
public class DBSettings {
    // match db.connection
    private String connection;
    // match db.user
    private String user;
    // match db.password
    private String password;

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
