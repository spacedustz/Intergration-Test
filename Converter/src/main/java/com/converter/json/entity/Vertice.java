package com.converter.json.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vertice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vertice_id")
    private Long id;

    private Double x;
    private Double y;

    @ManyToOne
    @JoinColumn(name = "extra_id")
    private Extra extra;

    private Vertice(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public static Vertice createOf(Double x, Double y) {
        return new Vertice(x,y);
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }
}
