package com.converter.csv;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Frame {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frame_id")
    private Long id;
    private int count;
    private Float frameTime;
    private String instanceId;

    @Column(nullable = true)
    private LocalDateTime systemDate;

    @Column(nullable = true)
    private Long systemTimestamp;

    private Frame(int count, Float frameTime, String instanceId, LocalDateTime systemDate, Long systemTimestamp) {
        this.count = count;
        this.frameTime = frameTime;
        this.instanceId = instanceId;
        this.systemDate = systemDate;
        this.systemTimestamp = systemTimestamp;
    }

    public static Frame createOf(int count, Float frameTime, String instanceId, LocalDateTime systemDate, Long systemTimestamp) {
        return new Frame(count, frameTime, instanceId, systemDate, systemTimestamp);
    }
}
