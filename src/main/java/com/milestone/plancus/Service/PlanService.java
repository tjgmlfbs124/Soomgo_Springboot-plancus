package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.*;
import com.milestone.plancus.Api.ResponseForm.TagsListResponse;
import com.milestone.plancus.Domain.*;
import com.milestone.plancus.Repository.MapRespository;
import com.milestone.plancus.Repository.PlanFilterDetailRepository;
import com.milestone.plancus.Repository.PlanFilterHeadRepository;
import com.milestone.plancus.Repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final PlanFilterHeadRepository headRepository;
    private final PlanRepository planRepository;
    private final PlanFilterDetailRepository detailRepository;
    private final MapRespository mapRespository;
    private final EntityManager em;

    @Transactional(readOnly = false)
    public Plan save(Plan plan){

        return planRepository.save(plan);
    }


    @Transactional(readOnly = false)
    public List<Plan> savePlanByHead(Long headId){

        List<Plan> savePlans = new ArrayList<>();

        PlanFilterHead findHead = headRepository.findById(headId).stream().findAny().get();

        for (PlanFilterDetail detail : findHead.getDetails()) {
            Plan savePlan = planRepository.save(new Plan(
                    findHead.getTitle(),
                    findHead.getColor(),
                    detail.getMember(),
                    findHead.getHost(),
                    findHead.getConfirmStartDate(),
                    findHead.getConfirmEndDate()
            ));
            savePlans.add(savePlan);
        }

        return savePlans;
    }

    public List<Plan> findAllDetailByHeadWithMember(Member member, LocalDateTime dateTime){

        return planRepository.findPlansByMemberWithDate(member.getId(), dateTime);
    }



    public void print(){

    }

    @Transactional(readOnly = true)
    public List<Plan> findPlanByMember(Member member) {
        Long id = member.getId();

        return planRepository.findPlanByMember(id);
    }

    @Transactional(readOnly = true)
    public List<Plan> findPlanByIdWithMember(Long planId,Member member) {
        Long id = member.getId();

        return planRepository.findPlanByIdWithMember(planId, member.getId());
    }

    public TagsListResponse findPlanTagsByMemeber(Member member, LocalDateTime startDate, LocalDateTime endDate){
        Long id = member.getId();
        LocalDateTime startDateTime = LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(0,0));
        LocalDateTime endDateTime = LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(23,59));

        List<Plan> plans =  planRepository.findPlansByMemberBetweenDate(member.getId(), startDateTime, endDateTime);

        TagsListResponse tags = new TagsListResponse();
        tags.setMain_business(new PlanTagDto("main-businiess"));
        tags.setSub_business(new PlanTagDto("sub-businiess"));
        tags.setDevelop(new PlanTagDto("friends"));
        tags.setFamily(new PlanTagDto("family"));
        tags.setFriend(new PlanTagDto("networking"));
        tags.setNetwork(new PlanTagDto("develop"));
        tags.setEtc(new PlanTagDto("etc"));

        for (Plan plan : plans) {
            // 일정일이 시작일과 종료이 같다 ? 카운트 시작 : 패스
           if (plan.getStartTime().toLocalDate().equals(plan.getEndTime().toLocalDate())){
               Duration duration = Duration.between(plan.getStartTime().toLocalTime(), plan.getEndTime().toLocalTime());

               switch (plan.getColor()){
                   case "main-business" :
                       tags.getMain_business().addSecond(duration.getSeconds());
                       break;
                   case "sub-business" :
                       tags.getSub_business().addSecond(duration.getSeconds());
                       break;
                   case "friends":
                       tags.getFriend().addSecond(duration.getSeconds());
                       break;
                   case "family":
                       tags.getFamily().addSecond(duration.getSeconds());
                       break;
                   case "develop":
                       tags.getDevelop().addSecond(duration.getSeconds());
                       break;
                   case "networking":
                       tags.getNetwork().addSecond(duration.getSeconds());
                       break;
                   case "etc":
                       tags.getEtc().addSecond(duration.getSeconds());
                       break;
               }
               tags.addTotalSecond(duration.getSeconds());
            }
        }

        return tags;
    }

    public List<Plan> findPrevPlan(LocalDateTime nextDateTime, Member member){
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0));
        LocalDateTime end = nextDateTime;

        List<Plan> prevPlans = planRepository.findAllPrevPlan(member.getId(), start, end);

        System.out.println("prevPlans = " + prevPlans);

        return prevPlans;

    }
}
