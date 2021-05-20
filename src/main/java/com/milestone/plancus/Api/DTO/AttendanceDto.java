package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Member;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AttendanceDto {
    private LocalDate date;
    private List<AvailableMember> morningMembers = new ArrayList<>();
    private List<AvailableMember> afterMembers = new ArrayList<>();
    private List<AvailableMember> eveningMembers = new ArrayList<>();

    @Data
    class AvailableMember{
        public AvailableMember(LocalTime startTime, LocalTime endTime, MemberDto member) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.member = member;
        }

        private LocalTime startTime;
        private LocalTime endTime;
        private MemberDto member;

        @Override
        public String toString() {
            return "{" +
                    "\"startTime\":\"" + startTime + "\""+
                    ", \"endTime\":\"" + endTime + "\""+
                    ", \"MemberDTO\":" + member +
                    '}';
        }
    }

    public void addMorningMember(LocalTime start, LocalTime end, Member member){
        boolean isMember = false;
        for (AvailableMember morningMember : morningMembers) {
            isMember = this.isEqual(morningMember, new AvailableMember(start, end, member.toDto()));
        }

        if (!isMember)
            morningMembers.add(new AvailableMember(start, end, member.toDto()));
    }

    public void addAfterMember(LocalTime start, LocalTime end, Member member){
        boolean isMember = false;

        for (AvailableMember morningMember : afterMembers) {
            isMember = this.isEqual(morningMember, new AvailableMember(start, end, member.toDto()));
        }

        if (!isMember)
            afterMembers.add(new AvailableMember(start, end, member.toDto()));
    }

    public void addEveningMember(LocalTime start, LocalTime end, Member member){
        boolean isMember = false;

        for (AvailableMember morningMember : eveningMembers) {
            isMember = this.isEqual(morningMember, new AvailableMember(start, end, member.toDto()));
        }

        if (!isMember)
            eveningMembers.add(new AvailableMember(start, end, member.toDto()));
    }

    private Boolean isEqual(AvailableMember args1, AvailableMember args2){

        return args1.startTime.equals(args2.startTime) &&
                args1.endTime.equals(args2.endTime) &&
                args1.getMember().getMember_id().equals(args2.getMember().getMember_id());

    }

    @Override
    public String toString() {
        return "{" +
                "\"date\":\"" + date + "\""+
                ", \"morningMembers\":" + morningMembers +
                ", \"afterMembers\":" + afterMembers +
                ", \"eveningMembers\":" + eveningMembers  +
                '}';
    }
}
