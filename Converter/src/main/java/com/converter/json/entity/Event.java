package com.converter.json.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@NoArgsConstructor
@Table(name = "EVENTS")
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "extra_id")
    private Extra extra;
    private Double frameId;

    private Event(Extra extra, Double frameId) {
        this.extra = extra;
        this.frameId = frameId;
    }

    public static Event createOf(Extra extra, Double frameId) {
        return new Event(extra,frameId);
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }
}
