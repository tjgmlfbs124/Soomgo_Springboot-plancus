package com.milestone.plancus.Controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;

@Controller
public class PlanController {

    @GetMapping("/plan/{action}")
    public String onSavePlan(@PathVariable("action") String action){
        action = action.toUpperCase(Locale.ROOT);

        return "/plan/" + action;
    }
}
