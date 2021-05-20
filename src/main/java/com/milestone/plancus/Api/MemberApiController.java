package com.milestone.plancus.Api;

import com.milestone.plancus.Api.DTO.MemberDto;
import com.milestone.plancus.Api.RequestForm.SigninMemberForm;
import com.milestone.plancus.Api.RequestForm.SignupMemberForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public List<MemberDto> signup(SignupMemberForm member, HttpSession httpSession){

        Member saveMember = new Member(member.getMember_id(), member.getMember_pw(), member.getMember_name(), member.getMember_role(), member.getMember_initial());

        return memberService.save(saveMember);

    }

    @PostMapping("/signin")
    public List<MemberDto> signin(SigninMemberForm member, HttpSession httpSession){
        List<Member> findMembers = memberService.findMemberIdWithPw(member);

        if (findMembers.size() > 0 )
            httpSession.setAttribute("member", findMembers.stream().findFirst().get());


        List<MemberDto> resultMembers = findMembers.stream().map(
                o -> new MemberDto(o.getMember_id(), o.getMember_name(), o.getMember_role(), o.getMember_initial())
        ).collect(Collectors.toList());

        return resultMembers;
    }

}
