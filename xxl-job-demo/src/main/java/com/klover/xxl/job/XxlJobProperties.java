package com.klover.xxl.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * XxlJobProperties
 *
 * @author klover
 * @date 2024/4/7 20:09
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {
    private String adminAddresses;
    private String accessToken;
    private String appName;
    private String address;
    private String ip;
    private int port;
    private String logPath;
    private int logRetentionDays;
}
