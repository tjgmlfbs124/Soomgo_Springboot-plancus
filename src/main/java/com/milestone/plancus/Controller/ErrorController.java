package com.milestone.plancus.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @GetMapping
    public String error400Page() {
        return "/error/error400";
    }

    @Override
    public String getErrorPath() {
        return "/errors";
    }
}
