package com.milestone.plancus.Controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;

@Controller
public class PlanController {

    @GetMapping("/plan/new")
    public String onPageSavePlan(){

        return "/plan/new";
    }
}
