package com.milestone.plancus.Domain;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class PlanFilter {
    public PlanFilter() {
    }

    public PlanFilter(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
