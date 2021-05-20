package com.milestone.plancus.Controller;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Service.PlanFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PlanFilterService filterService;

    @GetMapping("/")
    public String home(Model model, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");

        if (member == null)
            return "index";
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            return "/calender/monthly";
        }
    }

    @GetMapping("/sample/autocomplete")
    public String SampleAutoComplete(HttpSession session){
        return "/sample/autocomplete";

    }

    @GetMapping("/sample/getPoi")
    public String SamplegetPoi(HttpSession session){
        return "/sample/getPoi";

    }

    @GetMapping("/sample/routes")
    public String SampleRoutes(HttpSession session){
        return "/sample/routes";

    }
}
