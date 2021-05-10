package com.milestone.plancus.Service;

import com.milestone.plancus.Repository.MemberRepository;
import com.milestone.plancus.Repository.PlanRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanFilterServiceTest {

    @Autowired PlanRepository planRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void save(){
        /** Given **/


    }

}