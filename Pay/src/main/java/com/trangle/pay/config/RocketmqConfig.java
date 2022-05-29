package com.trangle.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author trangle
 */
@ConfigurationProperties(prefix = "rocketmq")
@Configuration
public class RocketmqConfig {

    public static final String address = "trangle:9876";

    private String nameServer;

    private String groupName;

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
