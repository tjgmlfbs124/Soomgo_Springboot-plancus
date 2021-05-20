package com.milestone.plancus.Domain;

import com.milestone.plancus.Api.DTO.TodoListDto;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TODOLIST")
@Getter
public class TodoList {
    public TodoList() {
    }

    public TodoList(Member member, Plan plan, String content) {
        this.content = content;
        this.member = member;
        this.plan = plan;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TD_CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TD_MEM_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TD_PLAN_ID")
    private Plan plan;

    @Column(name = "TD_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "TD_UPDATEBYDATE")
    private LocalDateTime updateByDate;

    public TodoListDto toDto(){
        return new TodoListDto(this.content, this.member, this.plan);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"content\":\"" + content + "\"" +
                ", \"member\":" + member +
                ", \"createByDate\":\"" + createByDate  + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                '}';
    }
}
