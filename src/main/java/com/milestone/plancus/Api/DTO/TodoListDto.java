package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.Plan;
import lombok.Data;

/**
 * not use
 */
@Data
public class TodoListDto {

    private String content;
    private String memberName;
    private String planName;

    public TodoListDto(String content, Member memberDto, Plan planDTO) {
        this.content = content;
        this.memberName = memberDto.getMember_name();
        this.memberName = planDTO.getTitle();
    }

    @Override
    public String toString() {
        return "{" +
                "\"content\":\"" + content + "\"" +
                '}';
    }
}
