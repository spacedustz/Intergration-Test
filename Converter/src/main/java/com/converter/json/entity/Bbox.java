package com.converter.json.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bbox {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bbox_id")
    private Long id;

    private Double height;
    private Double width;
    private Double x;
    private Double y;

    private Bbox(Double height, Double width, Double x, Double y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public static Bbox createOf(Double height, Double width, Double x, Double y) {
        return new Bbox(height,width,x,y);
    }
}
