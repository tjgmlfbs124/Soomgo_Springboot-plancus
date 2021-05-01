package com.milestone.plancus.Domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CalenderHead {
    public CalenderHead() {
    }

    public CalenderHead(String title, String color, Member member) {
        this.title = title;
        this.color = color;
        this.member = member;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CALH_ID")
    private Long id;

    @Column(name = "CALH_NAME")
    private String title;

    @Column(name = "CALH_COLOR")
    private String color;

    @OneToMany(mappedBy = "calenderHead")
    private List<CalenderDetail> calenderDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "MEM_ID")
    private Member member;

    @Column(name = "CALH_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "CALH_UPDATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;
}
