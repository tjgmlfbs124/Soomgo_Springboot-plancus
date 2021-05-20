package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.PlanFilterDetail;
import com.milestone.plancus.Domain.PlanFilterHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanFilterDetailRepository extends JpaRepository<PlanFilterDetail, Long> {

    @Query("select d from PF_DETAIL d where PFD_MEM_ID = :member_id and isRead = :state")
    List<PlanFilterDetail> findAllDetailByIsRead(
            @Param("member_id") Long member_id,
            @Param("state") Boolean state
    );

    @Query("select d from PF_DETAIL d where PFH_ID = :headId")
    List<PlanFilterDetail> findAllDetailByHead(@Param("headId") Long headId);


    @Query("select d from PF_DETAIL d where PFH_ID = :headId and PFD_MEM_ID = :member_id")
    List<PlanFilterDetail> findAllDetailByHeadWithMember(
            @Param("headId") Long headId,
            @Param("member_id") Long member);
}
