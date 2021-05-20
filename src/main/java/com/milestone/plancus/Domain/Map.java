package com.milestone.plancus.Domain;

import com.milestone.plancus.Api.DTO.MapDto;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Map {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAP_ID")
    private Long id;

    @Column(name = "MAP_NAME")
    private String name;

    @Column(name = "MAP_ADDRESS")
    private String address;

    @Column(name = "MAP_LON")
    private String lon;

    @Column(name = "MAP_LAT")
    private String lat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAP_PFH_ID")
    private PlanFilterHead head;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAP_PLAN_ID")
    private Plan plan;

    @Column(name = "CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    public Map() {
    }

    public Map(String name, String address, String lon, String lat, Plan plan, PlanFilterHead head) {
        this.name = name;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
        this.plan = plan;
        this.head = head;
    }

    public Map(String name, String address, String lon, String lat) {
        this.name = name;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
    }

    public Map addHead(PlanFilterHead head){
        this.head = head;

        return this;
    }

    public Map addPlan(Plan plan){
        this.plan = plan;

        return this;
    }

    public MapDto toDto(){
        return new MapDto(
                this.address,
                this.name,
                this.lat,
                this.lon
        );
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"name\":\"" + name  + "\"" +
                ", \"address\":\"" + address + "\"" +
                ", \"lon\":\"" + lon + "\"" +
                ", \"lat\":\"" + lat + "\"" +
                '}';
    }
}
