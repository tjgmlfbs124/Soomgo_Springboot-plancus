package com.milestone.plancus.Api;

import com.milestone.plancus.Api.DTO.*;
import com.milestone.plancus.Api.RequestForm.*;
import com.milestone.plancus.Api.ResponseForm.ConfirmListResponse;
import com.milestone.plancus.Api.ResponseForm.TagsListResponse;
import com.milestone.plancus.Domain.*;
import com.milestone.plancus.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlanApiController {
    private final PlanService planService;
    private final PlanFilterService filterService;
    private final PlanFilterAvaiableTimesService avaiableTimesService;
    private final TodoService todoService;
    private final MapService mapService;
    private final EntityManager em;

    @PostMapping("/plan/confirm")
    @Transactional(readOnly = false)
    public PlanFilterHeadDto savePlan(ConfirmPlanForm form, HttpSession httpSession){

        System.out.println("form = " + form.toString());
        Member member = (Member)httpSession.getAttribute("member");
        if (member == null)
            return null;
        else{
            PlanFilterHead head = filterService.confirmHead(form.getHeadId(), form.getStartTime(), form.getEndTime()); // 요청에 의해 확정된 Head를 리턴

            String address = head.getMap().getAddress();
            String name = head.getMap().getName();
            String lon = head.getMap().getLon();
            String lat = head.getMap().getLat();

            mapService.removeByHead(head.getId());

            List<Plan> plans = planService.savePlanByHead(form.getHeadId()); // headId를 가지고, Plan Rows 생성

            for (Plan plan : plans) {
                Map map = mapService.save(new Map(name, address, lon, lat, plan, null));
                mapService.save(map);
            }

            todoService.saveByHead(plans, head);

            return head.toPlanFilterHeadDTO();
        }
    }

    @GetMapping("/plan/month/all")
    public String findPlanByMember(HttpSession httpSession){
        Member member = (Member)httpSession.getAttribute("member");

        List<Plan> plans = planService.findPlanByMember(member);

        System.out.println("plans.toString() = " + plans.toString());

        return plans.toString();
    }

    @PostMapping("/plan/request")
    public PlanFilterHeadDto requestPlan(RequestPlanForm form, HttpSession httpSession){
        Member host = (Member) httpSession.getAttribute("member");

        System.out.println("form = " + form.toString());
        // 1. Head 저장
        PlanFilterHead head = filterService.save(form, host);

        // 2. Head에 일어날 장소 저장
        Map map = mapService.save(new Map(
                form.getMapName(),
                form.getMapAddress(),
                form.getMapLon(),
                form.getMapLat()).addHead(head)
        );

        PlanFilterHeadDto result = new PlanFilterHeadDto(
                head.getTitle(),
                head.getColor(),
                map.toDto(),
                head.getHost().getMember_name(),
                head.getLimitedDate(),
                head.getUseTimes(),
                head.getState(),
                head.getConfirmStartDate(),
                head.getConfirmEndDate()
        );

        System.out.println("result = " + result);

        return result;
    }

    @PostMapping("/plan/add")
    public PlanDTO addPlan(AddPlanForm form, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");

        System.out.println("form = " + form);

        Plan savePlan =  planService.save(new Plan(
                form.getTitle(),
                form.getColor(),
                member,
                member,
                form.getStartTime(),
                form.getEndTime()
        ));

        mapService.save(new Map(form.getMapName(), form.getMapAddress(), form.getMapLon(), form.getMapLat()).addPlan(savePlan));

        todoService.saveByPlan(savePlan, form.getTodoList(), member);

        return savePlan.toDTO();
    }

    @PostMapping("/plan/join")
    @Transactional(readOnly = false)
    public JoinPlanForm joinPlan(@RequestBody JoinPlanForm form, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");
        JoinDetailDto joinDto = filterService.findJoinsByHeadWithMember(form.getHeadId(), member);

        // 투표자수 <= 총 참석자수 ? 투표진행 : Head 상태 업데이트(예외처리)
        if (joinDto.getJoinMembers().size() <= joinDto.getPlanMembers().size()){
            
            boolean isJoin = joinDto.getJoinMembers().stream().anyMatch(m -> m.getId().equals(member.getId()));

            // 투표한 이력 ? 기존의 투표 지움 : 넘어감
            if (isJoin){
                PlanFilterHead head = filterService.findHeadById(form.getHeadId());
                PlanFilterDetail detail = head.getDetails().stream().filter(m -> m.getMember().getId().equals(member.getId())).collect(Collectors.toList()).stream().findFirst().get();
                avaiableTimesService.deleteAllByDetail(detail);
            }

            avaiableTimesService.saveByDetail(form.getHeadId(), member, form.getAvailableTimes());

            // DB에 저장된값을 가지고, 상태를 확인해야하기에, 영속성 컨테이너 초기화
            em.flush(); em.clear();

            // 로그인한 멤버의 투표를 저장 후, 다시 투표수를 조회 (다 투표했으면, 상태를 바꿔주기 위함)
            JoinDetailDto resultJoinDto = filterService.findJoinsByHeadWithMember(form.getHeadId(), member);

            System.out.println("resultJoinDto.toJson() = " + resultJoinDto.toJson());

            // 투표자수 >= 총 참석자수 ? Head 상태변경 : 넘어감
            if(resultJoinDto.getJoinMembers().size() >= resultJoinDto.getPlanMembers().size()){
                PlanFilterHead head = filterService.findHeadById(form.getHeadId());
                head.changeState(FilterType.CONFIRMING);

                filterService.saveHead(head);
                System.out.println("head = " + head.toJson());
            }
        }
        else{
            PlanFilterHead head = filterService.findHeadById(form.getHeadId());
            if (head.getState() == FilterType.WAITING)
                head.changeState(FilterType.CONFIRMING);
            filterService.saveHead(head);
        }
        return form;
    }

    @GetMapping("/plan/detailToJson")
    public String findDetailPlanByMember(@RequestParam("id") Long headId, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");

        if (member == null){
            return null;
        }
        else{
            filterService.updateIsRead(headId, member);

            JoinDetailDto filterByHeadWithMember = filterService.findJoinsByHeadWithMember(headId, member);

            System.out.println("filterByHeadWithMember.toJson() = " + filterByHeadWithMember.toJson());

            return filterByHeadWithMember.toJson();
        }
    }

    @GetMapping("/plan/confirm/find")
    public ConfirmListResponse findConfirmByAvailableDays(@RequestParam("id") Long headId, HttpSession httpSession){
        PlanFilterHead head = filterService.findHeadById(headId);

        Member member = (Member) httpSession.getAttribute("member");
        Member host = head.getHost();

        System.out.println("headId = " + headId);

        if (!member.getId().equals(host.getId())){
            return null;
        }
        else{
            ConfirmListResponse response = filterService.findConfirmByAvailableDays2(head);

            System.out.println("confirmByAvailableDays.toString() = " + response.toString());

            return response;
        }
    }

    @GetMapping("/plan/tags")
    public TagsListResponse findTagsByMemeber(
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("startDate")LocalDateTime startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("endDate") LocalDateTime endDate, HttpSession httpSession){

        Member member = (Member) httpSession.getAttribute("member");

        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);

        return planService.findPlanTagsByMemeber(member, startDate, endDate);
    }
}
