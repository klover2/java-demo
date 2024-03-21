package com.klover.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/login")
    @Operation(description = "登录")
    public String login() {
        return "true";
    }
}
