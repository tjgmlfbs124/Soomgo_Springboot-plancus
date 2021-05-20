package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import com.milestone.plancus.Domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("select distinct p from Plan p where MEM_ID = :member_id order by PLAN_ETIME")
    List<Plan> findPlanByMember(@Param("member_id") Long memberId);

    @Query("select p from Plan p where MEM_ID = :member_id and PLAN_ETIME <= :date")
    List<Plan> findPlansByMemberWithDate(
            @Param("member_id") Long memberId,
            @Param("date") LocalDateTime date);


    @Query("select p from Plan p where MEM_ID = :member_id and (PLAN_STIME > :startDate and PLAN_STIME <= :endDate)")
    List<Plan> findPlansByMemberBetweenDate(
            @Param("member_id") Long memberId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("select p from Plan p where id = :planId and MEM_ID = :memberId")
    List<Plan> findPlanByIdWithMember(
            @Param("planId") Long planId,
            @Param("memberId") Long memberId);

    @Query("select p from Plan p where MEM_ID = :memberId and (PLAN_STIME >= :startDateTime and PLAN_STIME < :endDateTime) order by PLAN_STIME")
    List<Plan> findAllPrevPlan(
            @Param("memberId") Long memberId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

}
