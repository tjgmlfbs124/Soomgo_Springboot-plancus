package com.milestone.plancus.Controller;

import com.milestone.plancus.Domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpSession httpSession){
        if ((Member) httpSession.getAttribute("member") == null)
            return "index";
        else{
            return "/calender/monthly";
        }
    }
}
