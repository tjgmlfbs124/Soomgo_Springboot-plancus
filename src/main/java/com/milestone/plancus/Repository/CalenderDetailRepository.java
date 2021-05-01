package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.CalenderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalenderDetailRepository extends JpaRepository<CalenderDetail, Long> {
}
