package com.ssafy.a603.lingoland.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.a603.lingoland.room.entity.Room;
import com.ssafy.a603.lingoland.room.entity.RoomId;

@Repository
public interface RoomRepository extends JpaRepository<Room, RoomId> {
}
