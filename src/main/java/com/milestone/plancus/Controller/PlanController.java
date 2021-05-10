package com.milestone.plancus.Controller;

import com.milestone.plancus.Api.DTO.FilterResult;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class PlanController {

    private final MemberService memberService;

    @GetMapping("/plan/request")
    public String onPageSavePlan(Model model, HttpSession httpSession){
        if ((Member)httpSession.getAttribute("member") == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("members",memberService.findAll());
            return "/plan/request";
        }
    }
}
