package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.Attendance;
import com.milestone.plancus.Api.DTO.FilterResult;
import com.milestone.plancus.Api.DTO.MemberDTO;
import com.milestone.plancus.Api.Form.FindPlanForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import com.milestone.plancus.Repository.MemberRepository;
import com.milestone.plancus.Repository.PlanRepository;
import org.json.simple.parser.ParseException;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanServiceTest {
    @Autowired PlanRepository planRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void save(){
        /** Given **/
        // 임시회원
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));
        memberRepository.save(createMember("DDDDD","aassbb","김윤환","사원", "D"));

        ArrayList<String> joinMemberIds = new ArrayList<>(Arrays.asList("AAAAA", "CCCCC", "DDDDD"));

        savePlan("티에이싱크 회의", "#FFFFFF",
                "북구 칠곡중앙대로",
                joinMemberIds,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("09:00")
                ),
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("12:00")
                )
        );

        flushToEntityManger();

        /** Result **/
    }

    /**
     * 테스트 데이터 입력후, 가능한 일정 시간 찾기
     * 구현방법
     * 1. LimitedDate 내에 참석하는 인원들의 모든 스케줄 DB를 통해 가져온다.
     * 2. 하루하루마다 각 참석한 인원들의 스케줄을 확인하여, 일정시간에 참석이 가능한지 조사한다.
     * 3. 각 하루마다 확인된 결과는, {Attendance} Class를 통해 관리한다. (List<Attendance> 구조)
     * attendance에 필요한 필드
     * {
     *     [Array] 참석가능한 인원
     *     [Array] 참석 불가능한 인원
     *     [LocalDateTime] 해당 날짜
     *     [int] 참석가능한 인원의 수
     * }
     * 4. 참석가능한 인원의 수를 가지고 정렬한다.
     */
    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void findAvailableTimes() throws ParseException {

        /** Given **/
        memberRepository.save(createMember("AAAAA","gmlfbs123","홍길동","대표", "H"));
        memberRepository.save(createMember("BBBBB","xhxh6040","엄석봉","사원", "U"));
        memberRepository.save(createMember("CCCCC","12341234","심청이","사원", "S"));
        memberRepository.save(createMember("DDDDD","aassbb","김삿갓","사원", "K"));
        memberRepository.save(createMember("EEEEE","gmlfbs123","메시","대리", "M"));
        memberRepository.save(createMember("FFFFF","xhxh6040","호날두","사원", "H"));
        memberRepository.save(createMember("GGGGG","12341234","아마테라스","사원", "A"));
        memberRepository.save(createMember("HHHHH","aassbb","이타치","사원", "E"));

        // 일정추가
        ArrayList<String> joinMemberIds1 = new ArrayList<>(Arrays.asList("AAAAA", "CCCCC", "DDDDD"));
        ArrayList<String> joinMemberIds2 = new ArrayList<>(Arrays.asList("AAAAA", "BBBBB", "CCCCC","DDDDD"));
        savePlan("티에이싱크 회의", "#FFFFFF",
                "대구 북구 칠곡중앙대로",
                joinMemberIds1,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("09:00")
                ),
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("12:00")
                )
        );
//        savePlan("티에이싱크 회의1", "#FFFFFF",
//                "대구 북구 칠곡중앙대로",
//                joinMemberIds1,
//                  "AAAAA",
//                LocalDateTime.of(
//                        LocalDate.now().plusDays(1),
//                        LocalTime.parse("09:00")
//                ),
//                LocalDateTime.of(
//                        LocalDate.now().plusDays(1),
//                        LocalTime.parse("12:00")
//                )
//        );
        savePlan("티에이싱크 회의2", "#FFFFFF",
                "대구 북구 칠곡중앙대로",
                joinMemberIds1,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now().plusDays(2),
                        LocalTime.parse("09:00")
                ),
                LocalDateTime.of(
                        LocalDate.now().plusDays(2),
                        LocalTime.parse("12:00")
                )
        );
        savePlan("티에이싱크 회의3", "#FFFFFF",
                "대구 북구 칠곡중앙대로",
                joinMemberIds1,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now().plusDays(3),
                        LocalTime.parse("09:00")
                ),
                LocalDateTime.of(
                        LocalDate.now().plusDays(3),
                        LocalTime.parse("12:00")
                )
        );
        savePlan(
                "마일리지365 회의", "#7DC668",
                "서울 강남구",
                joinMemberIds1,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("13:00")
                ),
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("18:00")
                )
        );

        FindPlanForm searchForm = createPlan(
                "동명회의",
                "#7DC668",
                LocalDateTime.now().plusDays(7),
                new ArrayList<>(Arrays.asList("AAAAA","CCCCC","FFFFF")),
                "동명",
                "09:00",
                "12:00",
                new ArrayList<>(Arrays.asList(1L, 3L, 5L))
        );

        flushToEntityManger();

        /** ACTION **/

        MemberDTO host = memberRepository.findMemberByLogId("AAAAA").stream().findFirst().get().toMemberDTO();

        // 클라이언트로 전달할 객체
        FilterResult filterResult = new FilterResult(
                searchForm.getTitle(),
                searchForm.getColor(),
                searchForm.getLocal(),
                LocalDate.now(),
                searchForm.getLimitedDays().toLocalDate(),
                LocalDateTime.of(LocalDate.now(),LocalTime.parse(searchForm.getAvailableStartTime())),
                LocalDateTime.of(LocalDate.now(),LocalTime.parse(searchForm.getAvailableEndTime())),
                searchForm.getAvailableDaysIndex());

        filterResult.setHost(host);
        
        LocalDate startSearchDate = LocalDate.from(LocalDateTime.now());            // 검색 시작날짜

        LocalDate limtedSearchDate = LocalDate.from(searchForm.getLimitedDays());   // 검색 종료날짜

        LocalTime availableStartTime = LocalTime.parse(searchForm.getAvailableStartTime()); // 요구한 일정 시작시간

        LocalTime availableEndTime = LocalTime.parse(searchForm.getAvailableEndTime());     // 요구한 일정 종료시간

        // 오늘부터 설정한 기간까지 하루씩 가능한 시간이 있는지 체크
        while(!startSearchDate.isEqual(limtedSearchDate)){
            Attendance attendance = new Attendance();
            attendance.setDate(startSearchDate);
            
            // 참석자에 한하여 하루씩 스케줄을 가져와서 비교
            for (String memberId : searchForm.getMemberIds()) {

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


        /** RESULT **/
        System.out.println("filterResult.toJsonString() = " + filterResult.toJsonString());
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void findPlansByMember(){
        /** Given **/
        // 임시회원
        memberRepository.save(createMember("AAAAA","gmlfbs123","서희륜","대리", "B"));
        memberRepository.save(createMember("BBBBB","xhxh6040","서교륜","사원", "Z"));
        memberRepository.save(createMember("CCCCC","12341234","곽록현","사원", "A"));
        memberRepository.save(createMember("DDDDD","aassbb","김윤환","사원", "D"));

        ArrayList<String> joinMemberIds1 = new ArrayList<>(Arrays.asList("AAAAA", "CCCCC", "DDDDD"));
        ArrayList<String> joinMemberIds2 = new ArrayList<>(Arrays.asList("AAAAA", "DDDDD", "BBBBB"));

        savePlan("티에이싱크 회의1", "#F80000",
                "북구 칠곡중앙대로1",
                joinMemberIds1,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("09:00")
                ),
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("12:00")
                )
        );

        savePlan("티에이싱크 회의2", "#FFFFFF",
                "북구 칠곡중앙대로2",
                joinMemberIds2,
                "AAAAA",
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("09:00")
                ),
                LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse("12:00")
                )
        );

        flushToEntityManger();

        /** ACTION **/
        Member sessionMember = memberRepository.findMemberByLogId("BBBBB").stream().findFirst().get();

        List<Plan> plans = planRepository.findPlansByMemberOrderByUpdateDate(sessionMember.getId());

        for (Plan plan : plans) {
            System.out.println("plan = " + plan.toJsonString());
        }
    }

    private Member createMember(String userId, String userPw, String userName, String userRole, String userInitial){

        return new Member(userId, userPw, userName, userRole, userInitial);
    }

    private void savePlan(String title, String color, String local, ArrayList<String> joinMemberIds, String hostId, LocalDateTime startDateTime, LocalDateTime endDateTime){
        Member host = memberRepository.findMemberByLogId(hostId).stream().findFirst().get();

        for (String joinMemberId : joinMemberIds) {

            Member member = memberRepository.findMemberByLogId(joinMemberId).stream().findFirst().get();

            Plan plan = new Plan(title, color, local, member, host, startDateTime, endDateTime);

            planRepository.save(plan);
        }
    }

    private FindPlanForm createPlan(String title, String color, LocalDateTime limitedDays, List<String> members, String local, String availableStartTime, String availableEndTime, List<Long> availableDaysIndex){
        return new FindPlanForm(
                title,
                color,
                limitedDays,
                members,
                local,
                availableStartTime,
                availableEndTime,
                availableDaysIndex
        );
    }

    private void flushToEntityManger(){
        em.flush();
        em.clear();
    }

}