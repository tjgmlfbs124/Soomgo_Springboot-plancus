package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.CalenderHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalenderHeadRepository extends JpaRepository<CalenderHead, Long> {
}
