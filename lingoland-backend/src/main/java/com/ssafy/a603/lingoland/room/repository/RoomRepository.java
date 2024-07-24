package com.ssafy.a603.lingoland.room.repository;

import com.ssafy.a603.lingoland.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
