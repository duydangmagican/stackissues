package com.example.issues.Config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class HerokuConfig {

    @Bean
    public DataSource dataSource() throws URISyntaxException {
            URI dbUri = new URI("postgres://ctfrepnxwoayzr:f7ac1dec69758fbafc1b38e9e83c52fb6e62bdf8ca84a6552c73412bb4c47d7b@ec2-107-20-15-85.compute-1.amazonaws.com:5432/dbp5pa1gpmjmvb");

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];

        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        DataSource data  = new DataSource();
        data.setUrl(dbUrl);
        data.setUsername(username);
        data.setPassword(password);

        return data;
    }
}
