package com.milestone.plancus.Controller;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.PlanFilterHead;
import com.milestone.plancus.Service.MemberService;
import com.milestone.plancus.Service.PlanFilterService;
import com.milestone.plancus.Service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class PlanController {
    private final PlanFilterService filterService;
    private final MemberService memberService;
    private final PlanService planService;

    /** AI 일정추가 페이지 **/
    @GetMapping("/plan/public/request")
    public String onPageRequestPublicPlan(Model model, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");
        if ( member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("members",memberService.findAll());
            return "/plan/public/request";
        }
    }

    /** 개인 일정추가 페이지 **/
    @GetMapping("/plan/private/add")
    public String onPageAddPrivatePlan(Model model, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");
        if ( member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("members",memberService.findAll());
            return "/plan/private/add";
        }
    }
    
    /** 공유일정 현황리스트 페이지 **/
    @GetMapping("/plan/public/list")
    public String onPagePublicPlanList(HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");

        if (member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("filters",filterService.findAllHeadByMember(member));
            return "plan/public/list";
        }
    }

    /** 모든일정 현황리스트 페이지 **/
    @GetMapping("/plan/list")
    public String onPagePlanList(HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");

        if (member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("plans", planService.findPlanByMember(member));

            System.out.println("planService.findPlanByMember(member) = " + planService.findPlanByMember(member));
            return "plan/list";
        }
    }

    /** 특정 공유일정 자세히보기 페이지 **/
    @GetMapping("/plan/public/detail")
    public String onPagePublicPlanDetail(@RequestParam("id") Long headId, HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");

        if (member == null){
            return "redirect:/";
        }
        else{
            PlanFilterHead head = filterService.findHeadById(headId);
            String url;

            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("member",member.toDto());

            // 일정공유 head의 상태에 따라서, 보여줄 웹페이지 다르게 
            switch (head.getState()){
                case WAITING:{ // 대기상태 : 일정공유 현황페이지
                    url = "plan/public/detail";
                    break;
                }
                case CONFIRMING: { // 확정중상태 : 로그인한 유저가 일정공유의 호스트 ?  일정공유 확정페이지 : 일정공유현황 페이지
                    if (head.getHost().getId().equals(member.getId()))
                        url = "redirect:/plan/public/confirm/list?id=" + head.getId();
                    else{
                        model.addAttribute("state", head.getState());
                        url = "plan/public/detail";
                    }
                    break;
                }
                case CONFIRM: { // 확정된 일정상태
                    url = "plan/public/confirm/detail";
                    break;
                }

                default:
                    url = "plan/public/detail";
            }

            return url;
        }
    }

    @GetMapping("/plan/public/confirm/list")
    public String onPagePublicPlanConfrimList(@RequestParam("id") Long headId, HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("member",member.toDto());
            return "plan/public/confirm/list";
        }
    }

    @GetMapping("/plan/public/confirm")
    public String onPagePublicPlanConfrimDetail(@RequestParam("id") Long headId, HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("member",member.toDto());
            return "plan/public/confirm/detail";
        }
    }

    @GetMapping("/plan/{type}")
    public String calender(@PathVariable("type") String type, HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null){
            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            model.addAttribute("isReadCount", filterService.findReadCount(member));

            return "calender/" + type;
        }
    }

    @GetMapping("/plan/chart")
    public String onPageChart(HttpSession httpSession, Model model){
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null){

            return "redirect:/";
        }
        else{
            model.addAttribute("isReadCount", filterService.findReadCount(member));
            return "plan/chart";
        }
    }
}
