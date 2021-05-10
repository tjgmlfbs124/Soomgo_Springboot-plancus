package com.milestone.plancus.Controller;

import com.milestone.plancus.Domain.Member;
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

    @GetMapping("/signout")
    public String signout(HttpSession httpSession){
        httpSession.removeAttribute("member");

        return "redirect:/";
    }

    @GetMapping("/calender/{type}")
    public String calender(@PathVariable("type") String type, HttpSession httpSession, Model model){
        if ((Member)httpSession.getAttribute("member") == null){
            return "redirect:/";
        }
        else{
            return "calender/" + type;
        }
    }

}
