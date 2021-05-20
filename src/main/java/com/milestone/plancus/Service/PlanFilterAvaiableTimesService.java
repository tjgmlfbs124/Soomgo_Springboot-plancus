package com.milestone.plancus.Service;

import com.milestone.plancus.Api.RequestForm.JoinPlanForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.PlanFilterAvailableTimes;
import com.milestone.plancus.Domain.PlanFilterDetail;
import com.milestone.plancus.Repository.PlanFilterAvailableTimesRepository;
import com.milestone.plancus.Repository.PlanFilterDetailRepository;
import com.milestone.plancus.Repository.PlanFilterHeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanFilterAvaiableTimesService {
    private final PlanFilterAvailableTimesRepository availableTimesRepository;
    private final PlanFilterHeadRepository headRepository;
    private final PlanFilterDetailRepository detailRepository;

    /** save()
     * 로그인한 사람의 투표를 저장한다
     * @param times : headId, 멤버정보, 투표내용등이 담겨있음
     * @return : 저장된 결과
     */
    @Transactional(readOnly = false)
    public PlanFilterAvailableTimes save(PlanFilterAvailableTimes times){

        return availableTimesRepository.save(times);
    }

    /** deleteAllByDetail()
     * 이미 투표한 정보를 모두 삭제한다
     * @param detail : 기존의 일정 정보
     */
    @Transactional(readOnly = false)
    public void deleteAllByDetail(PlanFilterDetail detail){
        availableTimesRepository.deleteAllByDetail(detail.getId());
    }

    /**
     * saveByDetail()
     * Head와 로그인한 사람과 투표내용을 취합하여 save를 호출
     * @param headId : 일정 head의 Id
     * @param member : 로그인한 사람의 정보
     * @param times : 투표한 희망시간
     * @return
     */
    @Transactional(readOnly = false)
    public List<JoinPlanForm.UseTimeDTO> saveByDetail(Long headId, Member member, List<JoinPlanForm.UseTimeDTO> times){
        PlanFilterDetail detail = detailRepository.findAllDetailByHeadWithMember(headId, member.getId()).stream().findFirst().get(); // 해당 일정에 속한 멤버의 상세 일정

        for (JoinPlanForm.UseTimeDTO time : times) {
            this.save(new PlanFilterAvailableTimes(detail, time.getStart(), time.getEnd()));
        }

        return times;
    }


    public List<PlanFilterAvailableTimes> findAllByDetail(PlanFilterDetail detail){

        return availableTimesRepository.findAllByDetail(detail.getId());
    }


}
