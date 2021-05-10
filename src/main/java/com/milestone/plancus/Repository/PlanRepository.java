package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("select p from Plan p where MEM_ID = :member_id order by updateByDate")
    List<Plan> findPlansByMemberOrderByUpdateDate(@Param("member_id") Long memberId);


}
