package com.milestone.plancus.Domain;

import com.milestone.plancus.Api.DTO.MapDto;
import com.milestone.plancus.Api.DTO.PlanFilterHeadDto;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "PF_HEAD")
@Getter
public class PlanFilterHead {

    public PlanFilterHead() {
    }

    public PlanFilterHead(String title, String color, Member host, LocalDateTime limitedDate, String useTimes, FilterType state, String todoList) {
        this.title = title;
        this.color = color;
        this.host = host;
        this.limitedDate = limitedDate;
        this.useTimes = useTimes;
        this.state = state;
        this.todoList = todoList;
    }

    @Id
    @Column(name = "PFH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PFH_TITLE")
    private String title;

    @Column(name = "PFH_COLOR")
    private String color;

    @OneToOne(mappedBy = "head")
    private Map map;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PFH_HOST_ID")
    private Member host;

    @Column(name = "PFH_LIMITDATE")
    private LocalDateTime limitedDate;

    @Column(name= "PFH_TIME")
    private String useTimes;

    @OneToMany(mappedBy = "headId" , cascade = CascadeType.ALL)
    private List<PlanFilterDetail> details = new ArrayList<>();

    @Column(name = "PFH_STATE")
    @Enumerated(EnumType.STRING)
    private FilterType state;

    @Column(name = "PFH_TODO")
    private String todoList;

    @Column(name = "PFH_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "PFH_UPDATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;

    @Column(name = "PFH_CONFIRM_SDATE")
    private LocalDateTime confirmStartDate;

    @Column(name = "PFH_CONFIRM_EDATE")
    private LocalDateTime confirmEndDate;

    public PlanFilterHead setConfirmStartDate(LocalDateTime time){
        this.confirmStartDate = time;

        return this;
    }

    public PlanFilterHead setConfirmEndDate(LocalDateTime time){
        this.confirmEndDate = time;

        return this;
    }

    public void addDetails(PlanFilterDetail detail){
        this.details.add(detail);
    }

    /** changeState()
     * Head의 상태를 param 값으로 변경
     * @param type : Enum class의 Type
     * @return : 변경된 type 값
     */
    public FilterType changeState(FilterType type){
        this.state = type;

        return this.state;
    }

    public PlanFilterHeadDto toPlanFilterHeadDTO(){
        return new PlanFilterHeadDto(
                this.getTitle(),
                this.getColor(),
                this.getMap().toDto(),
                this.getHost().getMember_name(),
                this.getLimitedDate(),
                this.getUseTimes(),
                this.getState(),
                this.confirmStartDate,
                this.confirmEndDate
        );
    }
    
    public String toJson(){
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":\"" + map + "\""+
                ", \"host\":" + host.toJson()  +
                ", \"limitedDate\":\"" + limitedDate  + "\""+
                ", \"useTimes\":\"" + useTimes  + "\""+
                ", \"details\":" + details  +
                ", \"createByDate\":\"" + createByDate + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                ", \"confirmDate\":\"" + confirmStartDate  + "\""+
                ", \"confirmEndDate\":\"" + confirmEndDate  + "\""+
                '}';
    }


}
