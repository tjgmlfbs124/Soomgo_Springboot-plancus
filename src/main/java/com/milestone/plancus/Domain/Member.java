package com.milestone.plancus.Domain;

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

    public Member(String member_id, String member_pw, String member_name, String member_role) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_name = member_name;
        this.member_role = member_role;
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

    @OneToMany(mappedBy = "member")
    private List<CalenderHead> calenders = new ArrayList<>();


    @Column(name = "MEM_CREATEBYDATE", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "MEM_UPDATEBYDATE", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;
}
