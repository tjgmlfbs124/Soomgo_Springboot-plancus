package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class JoinDetailDto {
    private MemberDto host; // 호스트 정보
    private MapDto map; // 호스트 정보
    private List<Member> planMembers = new ArrayList<>(); // 총 참석자
    private List<Member> joinMembers = new ArrayList<>(); // 투표 참여 참석자
    private List<UseTimeDTO> memberUseTimes = new ArrayList<>();

    public JoinDetailDto() {}

    public void addUseTimes(String startTime, String endTime){
        this.memberUseTimes.add(
                new UseTimeDTO(
                        startTime, endTime
                )
        );
    }

    public void addPlanMember(Member member){
        this.planMembers.add(member);
    }

    public void addJoinMember(Member member){
        this.joinMembers.add(member);
    }

    class UseTimeDTO{
        public UseTimeDTO(String startTime, String endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        private String startTime;
        private String endTime;

        public String toJson(){
            return "{" +
                    "\"startTime\":\"" + startTime + "\"" +
                    ", \"endTime\":\"" + endTime + "\"" +
                    '}';
        }

        @Override
        public String toString() {
            return "{" +
                    "\"startTime\":\"" + startTime + "\"" +
                    ", \"endTime\":\"" + endTime + "\"" +
                    '}';
        }
    }

    public String toJson(){
        return "{" +
                "\"planMembers\":" + planMembers +
                ", \"joinMembers\":" + joinMembers +
                ", \"memberUseTimes\":" + memberUseTimes.toString() +
                ", \"map\":" + map +
                ", \"host\":" + host +
                '}';
    }

    @Override
    public String toString() {

        return "{" +
                "\"planMembers\":" + planMembers +
                ", \"joinMembers\":" + joinMembers +
                ", \"memberUseTimes\":" + memberUseTimes +
                ", \"map\":" + map +
                ", \"host\":" + host +
                '}';
    }
}
