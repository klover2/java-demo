package com.klover.cloudwatchlog;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class CloudWatchLogTest {

    @Test
    void putLog() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key1", "val1");
        map.put("key2", "val2");
        CloudWatchLog.putLog(map);
    }
}