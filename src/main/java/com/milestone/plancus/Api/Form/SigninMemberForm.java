package com.milestone.plancus.Api.Form;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SigninMemberForm {
    private String member_id;
    private String member_pw;

    public SigninMemberForm(String member_id, String member_pw) {
        this.member_id = member_id;
        this.member_pw = member_pw;
    }
}
