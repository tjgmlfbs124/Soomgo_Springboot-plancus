package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.FilterType;
import com.milestone.plancus.Domain.Map;
import lombok.Data;
import org.thymeleaf.expression.Maps;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class PlanFilterHeadDto {
    private String title;
    private String color;
    private MapDto map;
    private String hostName;
    private LocalDateTime limitedDate;
    private String useTimes;
    private FilterType type;
    private LocalDateTime confirmStartDate;
    private LocalDateTime confirmEndDate;

    public PlanFilterHeadDto(String title, String color, MapDto map, String hostName, LocalDateTime limitedDate, String useTimes, FilterType type, LocalDateTime confirmStartDate, LocalDateTime confirmEndDate) {
        this.title = title;
        this.color = color;
        this.map = map;
        this.hostName = hostName;
        this.limitedDate = limitedDate;
        this.useTimes = useTimes;
        this.type = type;
        this.confirmStartDate = confirmStartDate;
        this.confirmEndDate = confirmEndDate;
    }

    @Override
    public String toString() {

        return "{" +
                "\"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":\"" + map + "\""+
                ", \"host\":" + hostName  +
                ", \"limitedDate\":\"" + limitedDate  + "\""+
                ", \"useTimes\":\"" + useTimes  + "\""+
                ", \"details\":" + type  +
                '}';
    }

    public String toJson(){
        return "{" +
                "\"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":\"" + map + "\""+
                ", \"host\":" + hostName  +
                ", \"limitedDate\":\"" + limitedDate  + "\""+
                ", \"useTimes\":\"" + useTimes  + "\""+
                ", \"details\":" + type  +
                '}';
    }
}
