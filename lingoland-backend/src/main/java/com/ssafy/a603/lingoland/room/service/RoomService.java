package com.ssafy.a603.lingoland.room.service;

import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.room.dto.RoomResponseDTO;
import com.ssafy.a603.lingoland.room.entity.Room;

public interface RoomService {

    public RoomResponseDTO createRoom(CustomUserDetails customUserDetails);

}
