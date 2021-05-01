package com.milestone.plancus.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @GetMapping("/signup")
    public String signup(){

        return "member/signup";
    }

    @GetMapping("/calender/{type}")
    public String calender(@PathVariable("type") String type, HttpSession httpSession, Model model){

        return "calender/" + type;
    }

}
