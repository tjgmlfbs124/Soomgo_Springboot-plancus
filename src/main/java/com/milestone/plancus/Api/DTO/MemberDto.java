package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Plan;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Data
@Getter
public class MemberDto {
    private String member_id;
    private String member_name;
    private String member_role;
    private String member_initial;
    private List<Plan> member_plans;

    public MemberDto() {
    }

    public MemberDto(String member_id, String member_name, String member_role, String member_initial) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_role = member_role;
        this.member_initial = member_initial;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDto memberDTO = (MemberDto) o;
        return Objects.equals(member_id, memberDTO.member_id) && Objects.equals(member_name, memberDTO.member_name) && Objects.equals(member_role, memberDTO.member_role) && Objects.equals(member_initial, memberDTO.member_initial) && Objects.equals(member_plans, memberDTO.member_plans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member_id, member_name, member_role, member_initial, member_plans);
    }

    @Override
    public String toString() {

        return "{" +
                "\"member_id\":\"" + member_id + "\"" +
                ", \"member_name\":\"" + member_name + "\"" +
                ", \"member_role\":\"" + member_role + "\""+
                ", \"member_initial\":\"" + member_initial  + "\""+
                '}';
    }
}
