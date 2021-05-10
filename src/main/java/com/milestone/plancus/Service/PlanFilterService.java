package com.milestone.plancus.Service;

import com.milestone.plancus.Domain.PlanFilterDetail;
import com.milestone.plancus.Repository.PlanFilterDetailRepository;
import com.milestone.plancus.Repository.PlanFilterHeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanFilterService {
    private final PlanFilterHeadRepository planFilterHeadRepository;
    private final PlanFilterDetailRepository planFilterDetailRepository;


}
