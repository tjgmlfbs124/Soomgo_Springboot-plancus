package com.milestone.plancus.Service;

import com.milestone.plancus.Api.RequestForm.AddPlanForm;
import com.milestone.plancus.Api.RequestForm.RequestPlanForm;
import com.milestone.plancus.Domain.*;
import com.milestone.plancus.Repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanServiceTest {
    @Autowired MemberRepository memberRepository;
    @Autowired PlanFilterAvaiableTimesService avaiableTimesService;
    @Autowired PlanFilterService filterService;
    @Autowired PlanService planService;
    @Autowired TodoService todoService;
    @Autowired MapService mapService;
    @Autowired EntityManager em;

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void savePlanByHead() {
        /** Given **/
        Member host = memberRepository.save(createMember("AAAAA", "gmlfbs123", "서희륜", "대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));

        RequestPlanForm form = new RequestPlanForm(
                "제목제목", "main-business",
                LocalDateTime.now().plusDays(7),
                new ArrayList<>(Arrays.asList("AAAAA","BBBBB","CCCCC")),
                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592",
                "01:00",
                new ArrayList<>(Arrays.asList("회의록작성","결재","팩스"))
        );

        PlanFilterHead saveHead1 = filterService.save(form, host);

        mapService.save(new Map(
                form.getMapName(),
                form.getMapAddress(),
                form.getMapLon(),
                form.getMapLat()).addHead(saveHead1));


        PlanFilterDetail targetDetail_1 = saveHead1.getDetails().get(0); // AAAAA 의견
        PlanFilterDetail targetDetail_2 = saveHead1.getDetails().get(1); // BBBBB 의견
        PlanFilterDetail targetDetail_3 = saveHead1.getDetails().get(2); // CCCCC 의견

        // 참석자가 가능한 시간 선택
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_1, "06:00", "08:59"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_1, "09:00", "17:59"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_1, "18:00", "22:00"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_2, "09:00", "17:59"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_3, "06:00", "08:59"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_3, "09:00", "17:59"));

        // 일정 확정짓기
        saveHead1.setConfirmStartDate(LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(9,0)
        ));

        saveHead1.setConfirmEndDate(LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(17,59)
        ));

        filterService.confirmHead(saveHead1.getId(), saveHead1.getConfirmStartDate(), saveHead1.getConfirmEndDate());

        flushToEntityManger();
        
        /** ACTION **/
        
        PlanFilterHead findHead = filterService.findHeadById(1L);

        String address = findHead.getMap().getAddress();
        String name = findHead.getMap().getName();
        String lon = findHead.getMap().getLon();
        String lat = findHead.getMap().getLat();

        mapService.removeByHead(findHead.getId());

        List<Plan> plans = planService.savePlanByHead(saveHead1.getId());

        for (Plan plan : plans) {
            Map save = mapService.save(new Map(name, address, lon, lat, plan, findHead));
            System.out.println("save = " + save.toString());
        }
    }


    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void findPrevPlan() {
        /** Given **/
        Member host = memberRepository.save(createMember("AAAAA", "gmlfbs123", "서희륜", "대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));

        AddPlanForm form1 = new AddPlanForm("CU 우유사먹기", "main-business", "CU", "서울 종로구 효자로13길 45", "14134188.64639495", "4520817.76302927", new ArrayList<>(Arrays.asList("관리", "오롤라")), LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30)), LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        AddPlanForm form2 = new AddPlanForm("티에이싱크 미팅", "main-business", "항신송산빌라", "경상북도 칠곡군 동명면", "14310486.27471157", "4298404.44825845", new ArrayList<>(Arrays.asList("노트북지참", "회의록작성")), LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 30)), LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 30)));
        AddPlanForm form3 = new AddPlanForm("하이버네이트 미팅", "main-business", "광주이노비즈센터", "광주 북구 추암로 249", "14122863.69278369", "4196157.64393600", new ArrayList<>(Arrays.asList("화장품", "펜 지참")), LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 30)), LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 30)));


        Plan plan1 = planService.save(new Plan(form1.getTitle(), form1.getColor(), host, host, form1.getStartTime(), form1.getEndTime()));
        Plan plan2 = planService.save(new Plan(form2.getTitle(), form2.getColor(), host, host, form2.getStartTime(), form2.getEndTime()));
        Plan plan3 = planService.save(new Plan(form3.getTitle(), form3.getColor(), host, host, form3.getStartTime(), form3.getEndTime()));

        mapService.save(new Map(form1.getMapName(), form1.getMapAddress(), form1.getMapLon(), form1.getMapLat()).addPlan(plan1));
        mapService.save(new Map(form2.getMapName(), form2.getMapAddress(), form2.getMapLon(), form2.getMapLat()).addPlan(plan2));
        mapService.save(new Map(form3.getMapName(), form3.getMapAddress(), form3.getMapLon(), form3.getMapLat()).addPlan(plan3));

        todoService.saveByPlan(plan1, form1.getTodoList(), host);
        todoService.saveByPlan(plan2, form2.getTodoList(), host);
        todoService.saveByPlan(plan3, form3.getTodoList(), host);

        flushToEntityManger();

        /** ACTION **/
        Plan plan = planService.findPlanByIdWithMember(3L, host).stream().findAny().get();

        List<Plan> prevPlan = planService.findPrevPlan(plan.getStartTime(), host);
        for (Plan findPlan : prevPlan) {
            System.out.println("findPlan = " + findPlan);
        }


    }

    private Member createMember(String userId, String userPw, String userName, String userRole, String userInitial){

        return new Member(userId, userPw, userName, userRole, userInitial);
    }

    private void flushToEntityManger(){
        em.flush();
        em.clear();
    }

}