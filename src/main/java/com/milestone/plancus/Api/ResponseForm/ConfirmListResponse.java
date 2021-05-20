package com.milestone.plancus.Api.ResponseForm;

import com.milestone.plancus.Api.DTO.MemberDto;
import com.milestone.plancus.Api.DTO.tempTestDto;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmListResponse {
    public ConfirmListResponse() {
    }

    private Long headId;
    private List<MemberDto> joinMembers;
    private List<tempTestDto> attendances;

    @Override
    public String toString() {
        return "{" +
                "\"headId\":" + headId +
                ", \"joinMembers\":" + joinMembers +
                ", \"attendances\":" + attendances +
                '}';
    }
}
