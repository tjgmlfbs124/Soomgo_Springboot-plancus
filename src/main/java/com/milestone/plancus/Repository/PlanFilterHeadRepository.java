package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.PlanFilterHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanFilterHeadRepository extends JpaRepository<PlanFilterHead, Long> {

    @Query(value="SELECT * FROM PF_HEAD LEFT JOIN PF_DETAIL ON PF_HEAD.PFH_ID = PF_DETAIL.PFH_ID WHERE PF_DETAIL.PFD_MEM_ID=:member_id", nativeQuery=true)
    List<PlanFilterHead> findAllByMember(
            @Param("member_id") Long member
    );
}
