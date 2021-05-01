package com.milestone.plancus.Domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class CalenderDetail {
    public CalenderDetail() {
    }

    public CalenderDetail(CalenderHead calenderHead, Member member, LocalDateTime startDate, LocalDateTime endDate) {
        this.calenderHead = calenderHead;
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CALH_ID")
    private CalenderHead calenderHead;

    @ManyToOne
    @JoinColumn(name = "MEM_ID")
    private Member member;

    @Column(name = "CALD_STIME")
    private LocalDateTime startDate;

    @Column(name = "CALD_ETIME")
    private LocalDateTime endDate;

    @Column(name = "CALD_CREATEBYDATE",insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "CALD_UPDATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;

}
