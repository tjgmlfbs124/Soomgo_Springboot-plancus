package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRespository extends JpaRepository<Map, Long> {
    @Modifying
    @Query("delete from Map where MAP_PFH_ID = :headId")
    void deleteMapByHeadId(@Param("headId") Long headId);
}
