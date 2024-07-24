package com.ssafy.a603.lingoland.room.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Room implements Serializable {

    // sequence setting required
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "room_id_seq", allocationSize = 1)
    private Integer id;

    @Transient
    private String code;

    @Transient
    private int memberCount = 1;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Room(String code){
        this.code = code;
    }

}
