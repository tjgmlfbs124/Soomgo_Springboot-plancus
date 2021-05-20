package com.milestone.plancus.Service;

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

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoServiceTest {
    @Autowired TodoService todoService;

    @Autowired MemberRepository memberRepository;
    @Autowired PlanFilterAvaiableTimesService avaiableTimesService;
    @Autowired PlanFilterService filterService;
    @Autowired MapService mapService;
    @Autowired PlanService planService;
    @Autowired EntityManager em;

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void save(){
        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));

        Member host = memberRepository.findMemberByLogId("AAAAA").stream().findFirst().get();

        // 호스트가 신청할 일정
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
        List<Plan> plans = planService.savePlanByHead(saveHead1.getId());

        /** ACTION **/
        System.out.println("saveHead1 == " + saveHead1.getTodoList());
        todoService.saveByHead(plans, saveHead1);

    }

    private Member createMember(String userId, String userPw, String userName, String userRole, String userInitial){

        return new Member(userId, userPw, userName, userRole, userInitial);
    }

    private void flushToEntityManger(){
        em.flush();
        em.clear();
    }

}