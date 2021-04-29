package com.milestone.plancus.Api;

import com.milestone.plancus.Api.DTO.MemberDTO;
import com.milestone.plancus.Api.Form.SigninMemberForm;
import com.milestone.plancus.Api.Form.SignupMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    @PostMapping("/signup")
    public MemberDTO signup(SignupMemberForm member, HttpSession httpSession){

        System.out.println("id = " + member.getMember_id());
        System.out.println("pw = " + member.getMember_pw());
        System.out.println("name = " + member.getMember_name());
        System.out.println("role = " + member.getMember_role());

        MemberDTO memberDTO = new MemberDTO(member.getMember_id(), member.getMember_name(), member.getMember_role());

        return memberDTO;
    }

    @PostMapping("/signin")
    public MemberDTO signin(SigninMemberForm member, HttpSession httpSession){

        MemberDTO memberDTO = new MemberDTO(member.getMember_id(), member.getMember_name(), member.getMember_role());

        return memberDTO;
    }
}
