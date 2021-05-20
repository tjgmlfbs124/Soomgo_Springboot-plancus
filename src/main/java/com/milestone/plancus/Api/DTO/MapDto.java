package com.milestone.plancus.Api.DTO;

import lombok.Data;

/**
 * 위도,경도,주소,이름을 가지고 있는 오브젝트
 */
@Data
public class MapDto{
    public MapDto(String address, String name, String lat, String lon) {
        this.address = address;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    private String address;
    private String name;
    private String lat;
    private String lon;

    @Override
    public String toString() {
        return "{"+
            " \"name\":\"" + name  + "\"" +
            ", \"address\":\"" + address + "\"" +
            ", \"lon\":\"" + lon + "\"" +
            ", \"lat\":\"" + lat  + "\"" +
            "}";
    }
}