package com.milestone.plancus.Api.DTO;


import com.milestone.plancus.Domain.Map;
import com.milestone.plancus.Domain.TodoList;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PlanDTO {
    private String title;
    private String color;
    private Map map;
    private MemberDto host;
    private List<TodoList> todoList = new ArrayList<>();
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PlanDTO() {
    }

    public PlanDTO(String title, String color, Map map, MemberDto host, List<TodoList> todoList, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.color = color;
        this.map = map;
        this.host = host;
        this.todoList = todoList;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":\"" + map + "\""+
                ", \"host\":" + host  +
                ", \"todoList\":" + todoList +
                ", \"startTime\":\"" + startTime  + "\""+
                ", \"endTime\":\"" + endTime  + "\""+
                '}';
    }
}
