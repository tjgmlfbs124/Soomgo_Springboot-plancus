package com.milestone.plancus.Api.RequestForm;

import com.milestone.plancus.Api.DTO.MapDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestPlanForm {
    public RequestPlanForm(String title, String color, LocalDateTime limitedDays, List<String> memberIds, String mapName, String mapAddress, String mapLon, String mapLat, String useTimes, List<String> todoList) {
        this.title = title;
        this.color = color;
        this.limitedDays = limitedDays;
        this.memberIds = memberIds;
        this.mapName = mapName;
        this.mapAddress = mapAddress;
        this.mapLon = mapLon;
        this.mapLat = mapLat;
        this.useTimes = useTimes;
        this.todoList = todoList;
    }

    private String title;
    private String color;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime limitedDays;

    private List<String> memberIds;

    private String mapName;
    private String mapAddress;
    private String mapLon;
    private String mapLat;

    private String useTimes;
    private List<String> todoList;

    public String todoListJoinToString(List<String> todoList){
        StringBuilder temp = new StringBuilder();
        if (todoList.size() > 0){

            for (String todo : todoList) {
                temp.append(todo).append("&&");
            }

            return temp.toString().substring(
                    0,
                    temp.toString().length()-2
            );
        }
        else{
            return null;
        }
    }

    public void setMap(MapDto dto){
        this.mapName = dto.getName();
        this.mapAddress = dto.getAddress();
        this.mapLat = dto.getLat();
        this.mapLon = dto.getLon();
    }

    public MapDto getMap(){
        return new MapDto(
                this.mapAddress,this.mapName, this.mapLat, this.mapLon
        );
    }

    @Override
    public String toString() {
        return "RequestPlanForm{" +
                "title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", limitedDays=" + limitedDays +
                ", memberIds=" + memberIds +
                ", map='" + getMap() + '\'' +
                ", useTimes=" + useTimes +
                ", todoList=" + todoList +
                '}';
    }
}
