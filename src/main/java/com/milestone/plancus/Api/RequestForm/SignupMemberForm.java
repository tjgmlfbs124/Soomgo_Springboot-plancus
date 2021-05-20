package com.milestone.plancus.Api.RequestForm;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignupMemberForm {
    private String member_id;
    private String member_name;
    private String member_pw;
    private String member_role;
    private String member_initial;
}
