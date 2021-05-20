package com.milestone.plancus.Controller;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Service.PlanFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
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

}
