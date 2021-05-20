package com.milestone.plancus.Api;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberApiControllerTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional(readOnly = true)
    @Rollback(value = false)
    public void save(){

    }


    public boolean isValidateMember(Member member){
        List<Member> findMembers = memberRepository.findMemberByLogId(member.getMember_id());
        System.out.println("findMembers = " + findMembers.size());
        return findMembers.size() > 0;
    }

}