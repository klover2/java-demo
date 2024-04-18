package com.klover.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author klover
 * @date 2024/3/20 18:25
 */
@RestController
@RequestMapping("/admin/test")
public class TestController {

    /**
     * 设置自定义sdk
     * https://docs.readme.com/main/docs/openapi-extensions#code-sample-languages
     * readme.com
     * @return
     */
    @PostMapping("/login")
    @Operation(description = "登录", summary = "login", extensions = {
            @Extension(name = "x-readme", properties = {
                    @ExtensionProperty(
                            name = "code-samples",
                            value = "[{\"language\":\"node\",\"code\":\"import Client from \\\"@ipeakoin/ipeakoin-sdk\\\"\\nconst client = new Client({\\n  clientId: '<your-client-id>',\\n  clientSecret: '<your-client-secret>',\\n  baseUrl: 'https://api-sandbox.ipeakoin.com',\\n});\\nconst codeRes = await client.getCode();\",\"name\":\"Api Request\",\"install\":\"npm install @ipeakoin/ipeakoin-sdk --save\"},{\"language\":\"java\",\"code\":\"private static AuthClient service = new AuthClient.Builder()\\n    .Credentials(\\\"ipeakoin1ab59eccfbc78d1b\\\", \\\"93fc39d77ef6a3a7b5f26b83fbbebe81\\\", \\\"https://api-sandbox.ipeakoin.com\\\")\\n    .build();\\nCodeRes res = service.getCode();\",\"name\":\"Api Request\",\"install\":\"<!-- https://mvnrepository.com/artifact/com.ipeakoin/ipeakoin-sdk -->\\n<dependency>\\n    <groupId>com.ipeakoin</groupId>\\n    <artifactId>ipeakoin-sdk</artifactId>\\n    <version>2.0.0</version>\\n</dependency>\"}]",
                            parseValue = true
                    ),
                    @ExtensionProperty(name = "samples-languages", value = "[\"node\",\"java\"]", parseValue = true)
            })
    })
    public String login() {
        return "true";
    }
}
