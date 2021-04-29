package com.milestone.plancus.Api.DTO;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MemberDTO {
    private String member_id;
    private String member_name;
    private String member_pw;
    private String member_role;

    public MemberDTO(String member_id, String member_name, String member_role) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_role = member_role;
    }
}
