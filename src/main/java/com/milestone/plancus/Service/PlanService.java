package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.Attendance;
import com.milestone.plancus.Api.DTO.FilterResult;
import com.milestone.plancus.Api.DTO.MemberDTO;
import com.milestone.plancus.Api.DTO.PlanDTO;
import com.milestone.plancus.Api.Form.FindPlanForm;
import com.milestone.plancus.Api.Form.SavePlanForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import com.milestone.plancus.Repository.MemberRepository;
import com.milestone.plancus.Repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.Filter;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    public FilterResult findAvailablePlanByMember(FindPlanForm form, Member host) throws ParseException {
        FilterResult filterResult = new FilterResult(
                form.getTitle(),
                form.getColor(),
                form.getLocal(),
                LocalDate.now(),
                form.getLimitedDays().toLocalDate(),
                LocalDateTime.of(LocalDate.now(),LocalTime.parse(form.getAvailableStartTime())),
                LocalDateTime.of(LocalDate.now(),LocalTime.parse(form.getAvailableEndTime())),
                form.getAvailableDaysIndex());

        filterResult.setHost(host.toMemberDTO());

        LocalDate startSearchDate = LocalDate.from(LocalDateTime.now());            // 검색 시작날짜

        LocalDate limtedSearchDate = LocalDate.from(form.getLimitedDays()).plusDays(1L);   // 검색 종료날짜

        LocalTime availableStartTime = LocalTime.parse(form.getAvailableStartTime()); // 요구한 일정 시작시간

        LocalTime availableEndTime = LocalTime.parse(form.getAvailableEndTime());     // 요구한 일정 종료시간

        // 오늘부터 설정한 기간까지 하루씩 가능한 시간이 있는지 체크
        while(!startSearchDate.isEqual(limtedSearchDate)){
            System.out.println("availableEndTime = " + startSearchDate.toString());
            Attendance attendance = new Attendance();
            attendance.setDate(startSearchDate);

            // 참석자에 한하여 하루씩 스케줄을 가져와서 비교
            for (String memberId : form.getMemberIds()) {

                Member member = memberRepository.findMemberByLogId(memberId).stream().findFirst().get(); // 참석자

                LocalDateTime startSearchDateTime = LocalDateTime.of(startSearchDate,availableStartTime);  // 요구한 일정 시작시간 [LocalDateTime]

                LocalDateTime endSearchDateTime = LocalDateTime.of(startSearchDate,availableEndTime);      // 요구한 일정 종료시간 [LocalDateTime]

                // 하루씩 증가하는 검색날짜안에서 요구한 일정시간이 되는지 찾아본다. (collect 값이 있으면 불가능, 없으면 가능)
                List<Plan> collect = member.getPlans().stream().filter(
                        dates -> !((dates.getStartTime().isBefore(startSearchDateTime) && dates.getStartTime().isBefore(endSearchDateTime)) ||
                                (dates.getEndTime().isAfter(startSearchDateTime) && dates.getEndTime().isAfter(endSearchDateTime)))
                ).collect(Collectors.toList());

                String isJoin = collect.size() > 0 ? "불가능" : "가능";

                System.out.println("\n" + member.getMember_name() + "님 " + startSearchDate.toString() +" [" + isJoin+ "]\n");

                if (collect.size() > 0){
                    attendance.addNegativeMember(member);
                }
                else{
                    attendance.addPositiveMember(member);
                }
                filterResult.addJoinMembers(member.toMemberDTO());
            }

            filterResult.addResult(attendance);

            startSearchDate = startSearchDate.plusDays(1L);
        }

        return filterResult;
    }

    public List<Long> savePlan(SavePlanForm form){

        ArrayList<Long> planIds = new ArrayList<>();

        LocalDateTime startTime = LocalDateTime.of(
                form.getSelectDate(),
                form.getFilterStartTime().toLocalTime()
        );

        LocalDateTime endTime = LocalDateTime.of(
                form.getSelectDate(),
                form.getFilterEndTime().toLocalTime()
        );

        Member host = memberRepository.findMemberByLogId(form.getHostId()).stream().findFirst().get();

        for (String positiveMemberId : form.getPositiveMemberIds()) {
            Member member = memberRepository.findMemberByLogId(positiveMemberId).stream().findFirst().get();

            Plan plan = new Plan(
                    form.getPlanTitle(),
                    form.getColor(),
                    form.getLocal(),
                    member,
                    host,
                    startTime,
                    endTime);

            planIds.add(planRepository.save(plan).getId());
        }

        return planIds;
    }

    public List<PlanDTO> findPlanByMember(Member member){
        Long id = member.getId();

        return planRepository.findPlansByMemberOrderByUpdateDate(id).stream().map(
                o -> new PlanDTO(
                        o.getId(),
                        o.getTitle(),
                        o.getColor(),
                        o.getLocal(),
                        o.getHost().toMemberDTO(),
                        o.getStartTime(),
                        o.getEndTime()
                )
        ).collect(Collectors.toList());
    }
}
