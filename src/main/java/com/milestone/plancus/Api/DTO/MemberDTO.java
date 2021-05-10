package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class MemberDTO {
    private String member_id;
    private String member_name;
    private String member_role;
    private String member_initial;
    private List<Plan> member_plans;

    public MemberDTO() {
    }

    public MemberDTO(String member_id, String member_name, String member_role, String member_initial) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_role = member_role;
        this.member_initial = member_initial;
    }



    @Override
    public String toString() {

        return "{" +
                "\"member_id\":\"" + member_id + "\"" +
                ", \"member_name\":\"" + member_name + "\"" +
                ", \"member_role\":\"" + member_role + "\""+
                ", \"member_initial\":\"" + member_initial  + "\""+
                ", \"member_plans\":" + member_plans  +
                '}';
    }
}
