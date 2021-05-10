package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Member;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Attendance {
    private List<MemberDTO> positiveMembers = new ArrayList<>();
    private List<MemberDTO> negativeMembers = new ArrayList<>();
    private LocalDate date;
    private int positiveCnt = 0;
    private int negativeCnt = 0;

    public Attendance() {
    }

    public Attendance(List<MemberDTO> positiveMembers, List<MemberDTO> negativeMembers, LocalDate date, int positiveCnt, int negativeCnt) {
        this.positiveMembers = positiveMembers;
        this.negativeMembers = negativeMembers;
        this.date = date;
        this.positiveCnt = positiveCnt;
        this.negativeCnt = negativeCnt;
    }

    public void addPositiveMember(Member member){
        this.positiveMembers.add(member.toMemberDTO());
        this.positiveCnt++;
    }

    public void addNegativeMember(Member member){
        this.negativeMembers.add(member.toMemberDTO());
        this.negativeCnt++;
    }

    @Override
    public String toString() {
        return "{" +
                "\"positiveMembers\":" + positiveMembers.toString() +
                ", \"negativeMembers\":" + negativeMembers.toString() +
                ", \"date\":\"" + date + "\""+
                ", \"positiveCnt\":\"" + positiveCnt  + "\""+
                ", \"negativeCnt\":\"" + negativeCnt  + "\""+
                '}';
    }

    public String toJsonString() {
        return "{" +
                "\"positiveMembers\":" + positiveMembers.toString() +
                ", \"negativeMembers\":" + negativeMembers.toString() +
                ", \"date\":\"" + date + "\""+
                ", \"positiveCnt\":\"" + positiveCnt  + "\""+
                ", \"negativeCnt\":\"" + negativeCnt  + "\""+
                '}';
    }
}
