package com.milestone.plancus.Service;

import com.milestone.plancus.Domain.Map;
import com.milestone.plancus.Repository.MapRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapService {
    private final MapRespository mapRespository;

    @Transactional(readOnly = false)
    public Map save(Map map){

        return mapRespository.save(map);
    }

    @Transactional(readOnly = false)
    public void removeByHead(Long headId){

        mapRespository.deleteMapByHeadId(headId);
    }

}
