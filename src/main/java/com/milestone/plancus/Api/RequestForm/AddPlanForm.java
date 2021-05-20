package com.milestone.plancus.Api.RequestForm;

import com.milestone.plancus.Api.DTO.MapDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddPlanForm {
    private String title;
    private String color;
    private String mapName;
    private String mapAddress;
    private String mapLon;
    private String mapLat;
    private List<String> todoList;

    public AddPlanForm(String title, String color, String mapName, String mapAddress, String mapLon, String mapLat, List<String> todoList, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.color = color;
        this.mapName = mapName;
        this.mapAddress = mapAddress;
        this.mapLon = mapLon;
        this.mapLat = mapLat;
        this.todoList = todoList;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    public MapDto getMap(){
        return new MapDto(
                this.mapAddress,this.mapName, this.mapLat, this.mapLon
        );
    }

    @Override
    public String toString() {
        return "{" +
                "\"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":\"" + getMap() + "\""+
                ", \"todoList\":" + todoList +
                ", \"startTime\":\"" + startTime  + "\""+
                ", \"endTime\":\"" + endTime  + "\""+
                '}';
    }
}
