package com.example.demo.controller;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test/msg")
    public Result<Map<String, String>> testConnect() {
        Map<String, String> map = Map.of(
                "serviceStatus", "后端服务运行正常",
                "serverPort", "8080",
                "tip", "前后端联调接口可正常访问"
        );
        return Result.success(map);
    }
}