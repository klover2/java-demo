package com.klover.sns;

import org.junit.jupiter.api.Test;

class SnsTest {

    @Test
    void pubTopic() {
        String message = "{\"test\":1,\"name\":\"klover\"}";
        Sns.pubTopic(message);
    }

    @Test
    void subscribe() {
        Sns.subscribe("https://www.xxx.cn/api", "https");
    }

    @Test
    void unsubscribe() {
        Sns.unsubscribe("<subscriptionArn>");
    }
}