package com.milestone.plancus.Domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name ="PF_DETAIL")
@Getter
public class PlanFilterDetail {
    public PlanFilterDetail() {
    }

    public PlanFilterDetail(PlanFilterHead headId, Member member,  Boolean isRead) {
        this.headId = headId;
        this.member = member;
        this.isRead = isRead;
    }

    public PlanFilterDetail(Long id, PlanFilterHead headId, Member member, Boolean isRead) {
        this.id = id;
        this.headId = headId;
        this.member = member;
        this.isRead = isRead;
    }

    @Id
    @Column(name = "PFD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PFH_ID")
    private PlanFilterHead headId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PFD_MEM_ID")
    private Member member;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL)
    private List<PlanFilterAvailableTimes> availableUseTimes = new ArrayList<>();

    @Column(name = "PFD_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "PFD_UPDATEBYDATE")
    private LocalDateTime updateByDate;

    @Column(name = "PFD_ISREAD")
    private Boolean isRead;

    public Boolean changeIsRead(Boolean isRead){
        this.isRead = isRead;

        return this.isRead;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"member\":" + member.toJson() +
                ", \"memberUseTimes\":" + availableUseTimes +
                ", \"createByDate\":\"" + createByDate + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                ", \"isRead\":\"" + isRead  + "\""+
                '}';
    }

    public String toJson(){
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"member\":" + member.toJson() +
                ", \"memberUseTimes\":" + availableUseTimes +
                ", \"createByDate\":\"" + createByDate + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                ", \"isRead\":\"" + isRead  + "\""+
                '}';
    }

}
