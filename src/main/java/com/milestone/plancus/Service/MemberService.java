package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.MemberDTO;
import com.milestone.plancus.Api.Form.SigninMemberForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberDTO> findAll(){
        List<Member> findMembers = memberRepository.findAll();
        List<MemberDTO> memberToDtos = findMembers.stream().map(
                o -> new MemberDTO(o.getMember_id(), o.getMember_name(), o.getMember_role(), o.getMember_initial())
        ).collect(Collectors.toList());

        return memberToDtos;
    }

    /** 회원 가입 **/
    public List<MemberDTO> save(Member member){
        List<MemberDTO> resultMembers = new ArrayList<>();

        if (!isValidateMember(member)){
            Member saveMember = memberRepository.save(member);

            resultMembers.add(new MemberDTO(saveMember.getMember_id(), saveMember.getMember_name(), saveMember.getMember_role(), saveMember.getMember_initial()));
        }
        return resultMembers;
    }

    /** 로그인 **/
    public List<Member> findMemberIdWithPw(SigninMemberForm memberForm){
        List<Member> findMembers = memberRepository.findMemberByLogIdWithPw(memberForm.getMember_id(), memberForm.getMember_pw());

        return findMembers;
    }
    
    /** 회원 로그인 ID를 가지고 멤버 Entity 검색 **/
    public List<Member> findMemberById(String loginId){

        return memberRepository.findMemberByLogId(loginId);
    }


    /** 회원가입 중복 검사**/
    public boolean isValidateMember(Member member){
        List<Member> findMembers = memberRepository.findMemberByLogId(member.getMember_id());

        return findMembers.size() > 0;
    }
}
