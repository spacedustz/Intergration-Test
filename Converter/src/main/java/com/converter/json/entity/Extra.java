package com.converter.json.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Extra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extra_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bbox_id")
    private Bbox bbox;

    @OneToMany(mappedBy = "extra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vertice> vertices = new ArrayList<>();

    private Extra(Bbox bbox, List<Vertice> vertices) {
        this.bbox = bbox;
        this.vertices = vertices;
    }

    public static Extra createOf(Bbox bbox, List<Vertice> vertices) {
        return new Extra(bbox,vertices);
    }

    public void setBbox(Bbox bbox) {
        this.bbox = bbox;
    }

    public void addVertice(Vertice vertice) {
        vertice.setExtra(this);
        if (this.vertices.isEmpty()) {
            this.vertices = new ArrayList<>(vertices);
        } else {
            this.vertices.add(vertice);
        }
    }
}
