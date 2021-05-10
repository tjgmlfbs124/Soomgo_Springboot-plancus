package com.milestone.plancus.Domain;

import com.milestone.plancus.Api.DTO.MemberDTO;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    public Member() {
    }

    public Member(String member_id, String member_pw, String member_name, String member_role, String member_initial) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_name = member_name;
        this.member_role = member_role;
        this.member_initial = member_initial;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEM_ID")
    private Long id;

    @Column(name = "MEM_LOGID", unique = true, nullable = false)
    private String member_id;

    @Column(name = "MEM_PASSWORD", nullable = false)
    private String member_pw;

    @Column(name = "MEM_NAME", nullable = false)
    private String member_name;

    @Column(name = "MEM_ROLE")
    private String member_role;

    @Column(name = "MEM_INITIAL")
    private String member_initial;

    @OneToMany(mappedBy = "member")
    private List<Plan> plans = new ArrayList<>();

    @Column(name = "MEM_CREATEBYDATE", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "MEM_UPDATEBYDATE", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;

    public MemberDTO toMemberDTO(){
        return new MemberDTO(
                this.getMember_id(),
                this.getMember_name(),
                this.getMember_role(),
                this.getMember_initial()
        );
    }
    public String toJsonString(){
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"member_id\":\"" + member_id  + "\"" +
                ", \"member_pw\":\"" + member_pw + "\"" +
                ", \"member_name\":\"" + member_name + "\""+
                ", \"member_role\":\"" + member_role  + "\""+
                ", \"member_initial\":\"" + member_initial  + "\""+
                ", \"member_role\":\"" + member_role  + "\""+
                ", \"createByDate\":\"" + createByDate  + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                '}';
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"member_id\":\"" + member_id  + "\"" +
                ", \"member_pw\":\"" + member_pw + "\"" +
                ", \"member_name\":\"" + member_name + "\""+
                ", \"member_role\":\"" + member_role  + "\""+
                ", \"member_initial\":\"" + member_initial  + "\""+
                ", \"member_role\":\"" + member_role  + "\""+
                ", \"createByDate\":\"" + createByDate  + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                '}';
    }
}
