package com.milestone.plancus.Api;

import com.milestone.plancus.Api.DTO.Attendance;
import com.milestone.plancus.Api.DTO.FilterResult;
import com.milestone.plancus.Api.DTO.MemberDTO;
import com.milestone.plancus.Api.DTO.PlanDTO;
import com.milestone.plancus.Api.Form.FindPlanForm;
import com.milestone.plancus.Api.Form.SavePlanForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import com.milestone.plancus.Domain.PlanFilterHead;
import com.milestone.plancus.Service.PlanService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlanApiController {
    private final PlanService planService;

    @PostMapping("/findPlan")
    public FilterResult findAvailablePlan(FindPlanForm form, HttpSession httpSession) throws ParseException {
        Member member = (Member)httpSession.getAttribute("member");
        if (member == null)
            return null;
        else{
            FilterResult filterResult = planService.findAvailablePlanByMember(form, member);
            System.out.println("filterResult = " + filterResult.toJsonString());

            return planService.findAvailablePlanByMember(form, member);
        }
    }

    @PostMapping("/plan/submitPlan")
    public List<Long> savePlan(SavePlanForm savePlanForm, Model model, HttpSession httpSession){
        Member member = (Member)httpSession.getAttribute("member");
        if (member == null)
            return null;
        else{
            List<Long> planIds = planService.savePlan(savePlanForm);

            System.out.println("planIds = " + planIds);

            return planIds;
        }
    }

    @GetMapping("/plan/month/all")
    public List<PlanDTO> findPlanByMember(HttpSession httpSession){
        Member member = (Member)httpSession.getAttribute("member");

        List<PlanDTO> plans = planService.findPlanByMember(member);

        for (PlanDTO plan : plans) {
            System.out.println("plan.toString() = " + plan.toString());
        }

        return plans;
    }

    @PostMapping("/plan/request")
    public PlanFilterHead requestPlan(PlanFilterHead headForm, HttpSession httpSession){

        System.out.println("headForm = " + headForm.toString());

        return headForm;
    }
}
