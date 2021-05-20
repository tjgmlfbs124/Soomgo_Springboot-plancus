package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.FilterDto;
import com.milestone.plancus.Api.DTO.JoinDetailDto;
import com.milestone.plancus.Api.RequestForm.RequestPlanForm;
import com.milestone.plancus.Domain.*;
import com.milestone.plancus.Repository.*;
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
public class PlanFilterServiceTest {

    @Autowired PlanFilterService filterService;
    @Autowired PlanService planService;
    @Autowired PlanRepository planRepository;
    @Autowired PlanFilterHeadRepository headRepository;
    @Autowired PlanFilterDetailRepository detailRepository;
    @Autowired PlanFilterAvaiableTimesService avaiableTimesService;
    @Autowired MemberRepository memberRepository;
    @Autowired MapService mapService;

    @Autowired EntityManager em;

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void saveFilterTest(){
        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));
        memberRepository.save(createMember("DDDDD","aassbb","김윤환","사원", "D"));

        flushToEntityManger();

        /** ACTION **/

        RequestPlanForm form = new RequestPlanForm(
                "제목제목", "#f8f8f8", LocalDateTime.now().plusDays(7),
                new ArrayList<>(Arrays.asList("AAAAA", "CCCCC", "DDDDD")),
                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592",
                "03:00", new ArrayList<>(Arrays.asList("회의록작성","결재","팩스")));
        Member host = memberRepository.findMemberByLogId("AAAAA").stream().findFirst().get();

        filterService.save(form, host);
    }

    @Test
    @Transactional(readOnly = true)
    @Rollback(value = false)
    public void findReadCount() {
        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));
        memberRepository.save(createMember("DDDDD","aassbb","김윤환","사원", "D"));

        Member member = memberRepository.findMemberByLogId("AAAAA").stream().findFirst().get();
        int count = filterService.findReadCount(member);

        System.out.println("count = " + count);


    }

    @Test
    @Transactional(readOnly = true)
    @Rollback(value = false)
    public void findFilterByMember() {
        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));
        memberRepository.save(createMember("DDDDD","aassbb","김윤환","사원", "D"));

        Member host = memberRepository.findMemberByLogId("CCCCC").stream().findFirst().get();

        int count = filterService.findReadCount(host);

        RequestPlanForm form = new RequestPlanForm(
                "제목제목",
                "#f8f8f8",
                LocalDateTime.now().plusDays(7),
                new ArrayList<>(Arrays.asList("AAAAA","CCCCC", "DDDDD")),
                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592",
                "03:00",
                new ArrayList<>(Arrays.asList("회의록작성","결재","팩스"))
        );

        PlanFilterHead saveHead1 = filterService.save(form, host);

        RequestPlanForm form2 = new RequestPlanForm(
                "얄라리양",
                "#f8f8f8",
                LocalDateTime.now().plusDays(7),
                new ArrayList<>(Arrays.asList("AAAAA", "BBBBB", "CCCCC", "DDDDD")),
                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592",
                "01:00",
                new ArrayList<>(Arrays.asList("회의록작성","결재","팩스")));

        PlanFilterHead saveHead2 = filterService.save(form2, host);

        for (PlanFilterDetail detail : saveHead1.getDetails()) {
            String start = LocalDateTime.now().plusHours(3).getHour() + "";
            String end = LocalDateTime.now().plusHours(6).getMinute() + "";
            avaiableTimesService.save(new PlanFilterAvailableTimes(
                    detail,
                    start.length() < 2 ? "0" + start : start,
                    end.length() < 2 ? "0" + end : end
            ));
        }

        flushToEntityManger();

        /** Action **/
        Member findMember = memberRepository.findMemberByLogId("AAAAA").stream().findAny().get();
        List<FilterDto> filters = filterService.findAllHeadByMember(findMember);

        for (FilterDto filter : filters) {
            for (PlanFilterAvailableTimes availableTimes : filter.getAvailableTimes()) {
                System.out.println("=======================================");
                System.out.println("availableTimes.getId() = " + availableTimes.getId());
                System.out.println("availableTimes.getId() = " + availableTimes.getDetail().toJson());
                System.out.println("availableTimes.getId() = " + availableTimes.getStartTime());
                System.out.println("availableTimes.getId() = " + availableTimes.getEndTime());
            }
        }

        System.out.println("filters = " + filters.toString());
    }

    @Test
    @Transactional(readOnly = true)
    @Rollback(value = false)
    public void findJoinMemberCount() {
        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));
        memberRepository.save(createMember("DDDDD","aassbb","김윤환","사원", "D"));

        Member host = memberRepository.findMemberByLogId("CCCCC").stream().findFirst().get();

        int count = filterService.findReadCount(host);

        RequestPlanForm form = new RequestPlanForm("제목제목", "#f8f8f8", LocalDateTime.now().plusDays(7), new ArrayList<>(Arrays.asList("AAAAA","CCCCC", "DDDDD")),
                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592","03:00", new ArrayList<>(Arrays.asList("회의록작성","결재","팩스")));

        PlanFilterHead saveHead1 = filterService.save(form, host);

        PlanFilterDetail targetDetail_1 = saveHead1.getDetails().get(0);
        PlanFilterDetail targetDetail_2 = saveHead1.getDetails().get(1);
        PlanFilterDetail targetDetail_3 = saveHead1.getDetails().get(2);

        RequestPlanForm form2 = new RequestPlanForm("얄라리양", "#f8f8f8", LocalDateTime.now().plusDays(7), new ArrayList<>(Arrays.asList("AAAAA", "BBBBB", "CCCCC", "DDDDD")),

                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592", "01:00", new ArrayList<>(Arrays.asList("회의록작성","결재","팩스")));

        PlanFilterHead saveHead2 = filterService.save(form2, host);


        String start = LocalDateTime.now().plusHours(3).getHour() + "";
        String end = LocalDateTime.now().plusHours(6).getMinute() + "";
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_1, start.length() < 2 ? "0" + start : start, end.length() < 2 ? "0" + end : end));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_2, start.length() < 2 ? "0" + start : start, end.length() < 2 ? "0" + end : end));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_3, start.length() < 2 ? "0" + start : start, end.length() < 2 ? "0" + end : end));

        flushToEntityManger();

        /** Action **/
        PlanFilterHead head = headRepository.findById(1L).get();
        Member member = memberRepository.findMemberByLogId("AAAAA").stream().findFirst().get();
        List<PlanFilterDetail> details = detailRepository.findAllDetailByHead(head.getId());
        JoinDetailDto joinDetailDTO = new JoinDetailDto();

        for (PlanFilterDetail detail : details) {

            joinDetailDTO.addPlanMember(detail.getMember());

            if (detail.getAvailableUseTimes().size() > 0){
                joinDetailDTO.addJoinMember(detail.getMember());

                if (member.equals(detail.getMember())){
                    for (PlanFilterAvailableTimes availableUseTime : detail.getAvailableUseTimes()) {

                        joinDetailDTO.addUseTimes(availableUseTime.getStartTime(), availableUseTime.getEndTime());
                    }
                }
            }
        }

        System.out.println("joinDetailDTO.toJson() = " + joinDetailDTO.toJson());

    }


    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void findConfirmByAvailableDays() {
        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));

        /** *
         * 첫번째 일정 확정시킴
         * */
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
        Map saveMap = mapService.save(new Map(
                form.getMap().getName(),
                form.getMap().getAddress(),
                form.getMap().getLon(),
                form.getMap().getLat()).addHead(saveHead1));

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
        planService.savePlanByHead(saveHead1.getId());

        System.out.println("============================================================");
        System.out.println("추가일정");
        System.out.println("============================================================");
        PlanFilterHead head = headRepository.findById(saveHead1.getId()).get();

        // 호스트가 신청할 일정
        RequestPlanForm form2 = new RequestPlanForm(
                "티에이싱크 회의", "main-business",
                LocalDateTime.now().plusDays(7),
                new ArrayList<>(Arrays.asList("AAAAA","BBBBB")),
                "항신송산빌라",
                "경상북도 칠곡군 동명면",
                "4298740.69038756",
                "14310133.78649592",
                "03:00",
                new ArrayList<>(Arrays.asList("회의록작성","결재","팩스")));

        PlanFilterHead saveHead2 = filterService.save(form2, host);

        PlanFilterDetail targetDetail_1_2 = saveHead2.getDetails().get(0); // AAAAA 의견
        PlanFilterDetail targetDetail_2_2 = saveHead2.getDetails().get(1); // BBBBB 의견

        // 참석자가 가능한 시간 선택
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_1_2, "06:00", "08:59"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_1_2, "09:00", "17:59"));
        avaiableTimesService.save(new PlanFilterAvailableTimes(targetDetail_2_2, "09:00", "17:59"));

        flushToEntityManger();

        /** ACTION **/
        PlanFilterHead findHead = headRepository.findById(saveHead2.getId()).get();
        filterService.findConfirmByAvailableDays2(findHead);

        System.out.println("saveMap = " + saveMap);




    }

    private Member createMember(String userId, String userPw, String userName, String userRole, String userInitial){

        return new Member(userId, userPw, userName, userRole, userInitial);
    }

    private void flushToEntityManger(){
        em.flush();
        em.clear();
    }

}