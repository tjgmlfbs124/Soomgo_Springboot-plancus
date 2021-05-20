package com.milestone.plancus.Controller;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import com.milestone.plancus.Domain.TodoList;
import com.milestone.plancus.Service.PlanFilterService;
import com.milestone.plancus.Service.PlanService;
import com.milestone.plancus.Service.TodoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.PublicKey;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final PlanService planService;
    private final PlanFilterService filterService;

    @GetMapping("/todo/detail")
    public String onPageTodoListDetail(@RequestParam("id") Long id, HttpSession httpSession, Model model){

        Member member = (Member) httpSession.getAttribute("member");
        if (member == null){
            return "redirect:/";
        }
        else{

            Plan plan = planService.findPlanByIdWithMember(id, member).stream().findFirst().get();

            List<Plan> prePlans = planService.findPrevPlan(plan.getStartTime(), member);
            Plan prePlan = new Plan();

            if (prePlans.size() > 0){

                prePlan = prePlans.get(prePlans.size()-1);
                model.addAttribute("prePlan", prePlan.toDTO());
            }

            System.out.println("plans = " + plan.toString());
            model.addAttribute("plan", plan.toDTO());
            model.addAttribute("prePlanCnt", prePlans.size());
            model.addAttribute("isReadCount", filterService.findReadCount(member));

            return "todoList/detail";
        }
    }

    @GetMapping("/todo/list")
    public String onPageTodoList( HttpSession httpSession, Model model ){

        Member member = (Member) httpSession.getAttribute("member");
        if (member == null){
            return "redirect:/";
        }
        else{
            List<Plan> plans = planService.findPlanByMember(member);

            System.out.println("plans = " + plans);
            model.addAttribute("plans", plans);
            model.addAttribute("isReadCount", filterService.findReadCount(member));

            return "todoList/list";
        }
    }
}
