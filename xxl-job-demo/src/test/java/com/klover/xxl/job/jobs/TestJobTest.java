package com.klover.xxl.job.jobs;

import com.ipeakoin.http.client.dto.res.ApiException;
import com.ipeakoin.utils.JsonUtil;
import com.ipeakoin.v1.auth.AuthClient;
import com.ipeakoin.v1.auth.dto.res.CodeRes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestJobTest {
    private static AuthClient service = new AuthClient.Builder()
            .Credentials("ipeakoin1ab59eccfbc78d1b", "93fc39d77ef6a3a7b5f26b83fbbebe81", "http://127.0.0.1:3000")
            .build();

    @Test
    void testJobHandler() throws ApiException {
        CodeRes code = service.getCode();
        System.out.println(JsonUtil.toJSONString(code));
    }
}