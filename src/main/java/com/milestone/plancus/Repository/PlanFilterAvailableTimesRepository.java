package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.PlanFilterAvailableTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanFilterAvailableTimesRepository extends JpaRepository<PlanFilterAvailableTimes, Long> {

    @Query("select t from MEMBER_UTIMES t where MEM_UTIEMS_DETAIL = :detail")
    List<PlanFilterAvailableTimes> findAllByDetail(@Param("detail") Long detailId);

    @Modifying
    @Query("delete from MEMBER_UTIMES t where MEM_UTIMES_DETAIL = :detail")
    void deleteAllByDetail(@Param("detail") Long detailId);


}
