package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.*;
import com.milestone.plancus.Api.RequestForm.RequestPlanForm;
import com.milestone.plancus.Api.ResponseForm.ConfirmListResponse;
import com.milestone.plancus.Domain.*;
import com.milestone.plancus.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanFilterService {
    private final PlanFilterHeadRepository headRepository;
    private final PlanFilterDetailRepository detailRepository;
    private final PlanFilterAvailableTimesRepository availableTimesRepository;
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;


    /** findHeadById()
     * Head의 Id를 가져와 Head 객체를 찾는다.
     * @param id : 찾을 Head의 Id
     * @return : [PlanFilterHead]
     */
    @Transactional(readOnly = true)
    public PlanFilterHead findHeadById(Long id){
        return headRepository.findById(id).get();
    }

    /** saveHead()
     * Head를 저장
     * @param head : 저장할 head 정보
     * @return : [PlanFilterHead]
     */
    @Transactional(readOnly = false)
    public PlanFilterHead saveHead(PlanFilterHead head){
        return headRepository.save(head);
    }

    /** findReadCount()
     * 로그인한 유저가 일정정보를 읽었는지 확인
     * @param member : 로그인한 유저 정보
     */
    @Transactional(readOnly = true)
    public int findReadCount(Member member){
        List<PlanFilterDetail> isReadsByMember = detailRepository.findAllDetailByIsRead(member.getId(), Boolean.FALSE);

        return isReadsByMember.size();
    }

    /** updateIsRead()
     * Detail의 isRead 상태를 변경해줌 (클라이언트가 detail 일정을 확인할때 호출)
     * @param headId : 선택한 일정 head Id
     * @param member : 로그인한 멤버 정보
     */
    @Transactional(readOnly = false)
    public void updateIsRead(Long headId, Member member){
        PlanFilterDetail detail = detailRepository.findAllDetailByHeadWithMember(headId, member.getId()).stream().findFirst().get();

        if (!detail.getIsRead()){
            System.out.println("detail = " + detail);
            detail.changeIsRead(true);

            detailRepository.save(new PlanFilterDetail(detail.getId(), detail.getHeadId(), detail.getMember(), detail.getIsRead()));
        }
    }

    /** PlanHead에 맞는 정보(JoinDetailDTO)를 가져옴
     * 정보
     *  참석자
     *  투표한 참석자
     *  내 참석여부
     * **/
    @Transactional(readOnly = true)
    public JoinDetailDto findJoinsByHeadWithMember(Long headId, Member member){
        PlanFilterHead head = headRepository.findById(headId).get();
        List<PlanFilterDetail> details = detailRepository.findAllDetailByHead(head.getId());
        JoinDetailDto joinDetailDTO = new JoinDetailDto();
        joinDetailDTO.setHost(head.getHost().toDto());
        joinDetailDTO.setMap(head.getMap().toDto());

        for (PlanFilterDetail detail : details) {
            joinDetailDTO.addPlanMember(detail.getMember());
            if (detail.getAvailableUseTimes().size() > 0){
                joinDetailDTO.addJoinMember(detail.getMember());

                if (member.getId().equals(detail.getMember().getId())){
                    for (PlanFilterAvailableTimes availableUseTime : detail.getAvailableUseTimes()) {
                        joinDetailDTO.addUseTimes(availableUseTime.getStartTime(), availableUseTime.getEndTime());
                    }
                }
            }
        }

        return joinDetailDTO;
    }

    /** save()
     * head와 detail을 한번에 저장.
     *  @form : 클라이언트로부터 받은 정보 
     *  @host : 현재 로그인한 유저 정보
     * **/
    @Transactional(readOnly = false)
    public PlanFilterHead save(RequestPlanForm form, Member host){
        // Save To PlanFilterHead
        PlanFilterHead head = new PlanFilterHead(
                form.getTitle(),
                form.getColor(),
                host,
                form.getLimitedDays(),
                form.getUseTimes(),
                FilterType.WAITING,
                form.todoListJoinToString(form.getTodoList())
                );

        // Save To PlanFilterDetail
        for (String memberId : form.getMemberIds()) {
            head.addDetails(new PlanFilterDetail(
                    head,
                    memberRepository.findMemberByLogId(memberId).stream().findFirst().get(),
                    false
            ));
        }

        return this.saveHead(head);
    }

    /** save()
     * 로그인한 멤버의 모든 일정필터를 보여줌
     *  @member : 현재 로그인한 유저 정보
     * **/
    @Transactional(readOnly = true)
    public List<FilterDto> findAllHeadByMember(Member member){
        List<PlanFilterHead> heads = headRepository.findAll();
        List<FilterDto> filters = new ArrayList<>();
        for (PlanFilterHead head : heads) {
            boolean isMember = head.getDetails().stream().anyMatch(m -> m.getMember().equals(member));

            if (isMember){
                FilterDto filter = new FilterDto(
                        head.getId(),
                        head.getTitle(),
                        head.getColor(),
                        head.getHost(),
                        head.getState(),
                        head.getCreateByDate(),
                        head.getDetails().stream().filter(
                                o -> o.getMember().equals(member)
                        ).findFirst().get().getIsRead(),
                        head.getDetails().stream().filter(
                                o -> o.getMember().equals(member)
                        ).findFirst().get().getAvailableUseTimes().size() > 0
                );

                filters.add(filter);
            }
        }
        return filters;
    }

    /** findConfirmByAvailableDays()
     * 일정과 투표시간을 고려한 최적화된 일정 List를 보여준다.
     *  @member : 현재 로그인한 유저 정보
     * **/
    @Transactional(readOnly = true)
    public ConfirmListResponse findConfirmByAvailableDays2(PlanFilterHead head){
        ConfirmListResponse response = new ConfirmListResponse();

        PlanFilterHead findHead = headRepository.findById(head.getId()).get();
        List<Member> joinMember = memberRepository.findMemberByDetail(head.getId());

        List<tempTestDto> test = new ArrayList<>();

        response.setJoinMembers(joinMember.stream().map(Member::toDto).collect(Collectors.toList()));
        response.setHeadId(head.getId());

        LocalDate startSearchDate = LocalDate.from(LocalDateTime.now());        // 일정 검색 시작날짜 (오늘)
        LocalDate limtedSearchDate = LocalDate.from(findHead.getLimitedDate()); // 일정 검색 종료날짜 (호스트가 정한 기간)


        LocalDateTime limtedSearchDateTime = LocalDateTime.of(
                LocalDate.from(findHead.getLimitedDate()),
                LocalTime.of(23,59));


        // 사용할 시간
        LocalTime useTimes = LocalTime.of(Integer.parseInt(head.getUseTimes().split(":")[0]), Integer.parseInt(head.getUseTimes().split(":")[1]));

        List<AttendanceDto> attendances = new ArrayList<>();

        // 아침시간, 업무시간, 저녁시간의 시작시간 고정
        List<LocalTime> startTimes = new ArrayList<>(Arrays.asList(LocalTime.of(6,0), LocalTime.of(9,0), LocalTime.of(18,0)));

        // 오늘부터 설정한 기간까지 하루씩 가능한 시간이 있는지 체크
        while(!startSearchDate.isAfter(limtedSearchDate)){

            System.out.println("============================================================");
            System.out.println("startSearchDate = " + startSearchDate);
            System.out.println();

            AttendanceDto attendance = new AttendanceDto();
            attendance.setDate(startSearchDate);

            for (Member member : joinMember) {

                System.out.println("============================================================");
                System.out.println(member.getMember_name() + "님 " + startSearchDate + " 상황");

                // 6 9

                /**
                 * 1. 고객이 선택한 시간대에 계획이 있는지 ? 계획을 살핀다 : 그냥 바로 가능한것임.
                 */
                List<Plan> memberPlans = planRepository.findPlansByMemberWithDate(member.getId(), limtedSearchDateTime);

                // 멤버에 일정이 있는가? 일정 살펴보기 : 멤버가 선택한 시간 살펴보기
                if (memberPlans.size() > 0){

                    System.out.println("이 멤버의 일정을 검색합니다..");

                    List<tempTestDto> memberToList = new ArrayList<>();
                    List<LocalTime> startTimesList = new ArrayList<>();

                    for (LocalTime startTime : startTimes) { // 6 9 18
                        startTimesList.add(startTime);
                        System.out.println("startTime = " + startTime);

                        // 검색하려는 시간이 현재시간을 넘었는가 ? 패스 : 선택한 시간을 본다.
                        if (!LocalDateTime.of(startSearchDate, startTime).isBefore(LocalDateTime.now())){

                            PlanFilterDetail detail = detailRepository.findAllDetailByHeadWithMember(head.getId(), member.getId()).stream().findAny().get();

                            // 멤버가 선택한 시간만큼 반복
                            for (PlanFilterAvailableTimes availableUseTime : detail.getAvailableUseTimes()) {

                                tempTestDto aaaa = new tempTestDto(startSearchDate, startTime.toString() + "~" + startTime.plusHours(useTimes.getHour()).plusMinutes(useTimes.getMinute()));

                                // 일정이 끝나는 시간 ( 참여멤버 요구시간 + 소요시간 )
                                LocalTime availableEndTime = startTime.plusHours(useTimes.getHour()).plusMinutes(useTimes.getMinute());
                                System.out.println("endTime = " + availableEndTime);

                                for (Plan plan : memberPlans) {
                                    LocalDateTime memberAvailableStartTime = LocalDateTime.of(startSearchDate, startTime); // 해당 멤버가 가능한 시작시간
                                    LocalDateTime memberAvailableEndTime = LocalDateTime.of(startSearchDate, availableEndTime);     // 해당 멤버가 가능한 종료시간

                                    LocalDateTime planStartTime = plan.getStartTime();
                                    LocalDateTime planEndTime = plan.getEndTime();

                                    if (((memberAvailableStartTime.isBefore(planStartTime) && memberAvailableEndTime.isBefore(planStartTime)) || (memberAvailableStartTime.isAfter(planEndTime) && memberAvailableEndTime.isAfter(planEndTime)))){
                                        System.out.println("일정이 겹치지않습니다");
                                        System.out.println("인덱스 시간  = " +  memberAvailableStartTime + "~" + memberAvailableEndTime);
                                        System.out.println("일정 시간  = " +  planStartTime + "~" + planEndTime);

                                        if (startTime.equals(LocalTime.of(Integer.parseInt(availableUseTime.getStartTime().split(":")[0]), Integer.parseInt(availableUseTime.getStartTime().split(":")[1])))) {
                                            aaaa.setMember(member.toDto());
                                            memberToList.add(aaaa);
                                        }


//                                        if (startSearchDate.equals(LocalDate.from(planStartTime))){
//                                            System.out.println("aaaa  = " + aaaa );
//                                            aaaa.setMember(member.toDto());
//                                            memberToList.add(aaaa);
//                                        }
                                        System.out.println();
                                    }
                                    else{
                                        System.out.println("일정이 겹칩니다");
                                        System.out.println("인덱스 시간  = " +  memberAvailableStartTime + "~" + memberAvailableEndTime);
                                        System.out.println("일정 시간  = " +  planStartTime + "~" + planEndTime);
                                        System.out.println();
                                    }
                                }
                            }
                        }
                    }
                    test.addAll(fillToList(startTimesList, useTimes, memberToList, startSearchDate));
                    System.out.println("fillToList(startTimesList, useTimes, memberToList, startSearchDate) = " + fillToList(startTimesList, useTimes, memberToList, startSearchDate));
                    
                }
                else {
                    List<tempTestDto> memberToList = new ArrayList<>();
                    List<LocalTime> startTimesList = new ArrayList<>();

                    for (LocalTime startTime : startTimes) { // 6  9  18
                        startTimesList.add(startTime);

                        if (!LocalDateTime.of(startSearchDate,startTime).isBefore(LocalDateTime.now())){
                            PlanFilterDetail detail = detailRepository.findAllDetailByHeadWithMember(head.getId(), member.getId()).stream().findAny().get();

                            for (PlanFilterAvailableTimes availableUseTime : detail.getAvailableUseTimes()) { // 6  9  18
                                tempTestDto aaaa = new tempTestDto(startSearchDate, startTime.toString() + "~" + startTime.plusHours(useTimes.getHour()).plusMinutes(useTimes.getMinute()));

                                if (startTime.equals(LocalTime.of(Integer.parseInt(availableUseTime.getStartTime().split(":")[0]), Integer.parseInt(availableUseTime.getStartTime().split(":")[1])))) {
                                    aaaa.setMember(member.toDto());
                                    memberToList.add(aaaa);
                                }

                            }
                        }
                    }

                    test.addAll(fillToList(startTimesList, useTimes, memberToList, startSearchDate));
                }
                System.out.println("============================================================");
            }
            attendances.add(attendance);
            startSearchDate = startSearchDate.plusDays(1L);
        }
        System.out.println("attendances = " + attendances);
        System.out.println("test = " + test.toString());
        response.setAttendances(test);

        return response;
    }

    @Transactional(readOnly = false)
    public PlanFilterHead confirmHead(Long headId, LocalDateTime startTime, LocalDateTime endTime){

        PlanFilterHead head = headRepository.findById(headId).get();

        head.changeState(FilterType.CONFIRM);
        head.setConfirmStartDate(startTime);
        head.setConfirmEndDate(endTime);

        headRepository.save(head);

        return head;
    }

    public List<tempTestDto> fillToList(List<LocalTime> startTimes, LocalTime useTime, List<tempTestDto> dtos, LocalDate date){
        List<tempTestDto> temp = dtos;
        for (LocalTime startTime : startTimes) { // 6 9 12
            if (!dtos.toString().contains(startTime.toString())) {
                String endTime = startTime.plusHours(useTime.getHour()).plusMinutes(useTime.getMinute()).toString();
                if (!LocalDateTime.of(date,startTime).isBefore(LocalDateTime.now())) {
                    temp.add(new tempTestDto(date, startTime + "~" + endTime));
                }

            }

        }

        return temp;
    }

}
