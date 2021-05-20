package com.milestone.plancus.Api.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class tempTestDto {
    public tempTestDto(LocalDate date, String time) {
        this.date = date;
        this.time = time;
    }

    private LocalDate date;
    private String time;
    private MemberDto member;

    @Override
    public String toString() {
        return "{" +
                "\"date\":\"" + date + "\""+
                ", \"time\":\"" + time + "\""+
                ", \"member\":" + member +
                '}';
    }

    public Boolean isEqual(tempTestDto dto){

        if (dto.getMember() == null) return false;

        return this.date.isEqual(dto.getDate())&&
                this.time.equals(dto.time) &&
                this.member.getMember_id().equals(dto.getMember().getMember_id());
    }
}
