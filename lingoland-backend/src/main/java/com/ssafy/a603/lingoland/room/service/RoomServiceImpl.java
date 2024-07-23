package com.ssafy.a603.lingoland.room.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.room.dto.RoomResponseDTO;
import com.ssafy.a603.lingoland.room.entity.Room;
import com.ssafy.a603.lingoland.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    public RoomResponseDTO createRoom(CustomUserDetails customUserDetails){

        String code = getLoginIdFromUserDetails(customUserDetails);
        Room room = Room.builder()
                .code(code)
                .build();

        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(getRoomKey(code), room);
        Room createdRoom = (Room) valueOps.get(getRoomKey(code));

        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .code(createdRoom.getCode())
                .memberCount(createdRoom.getMemberCount())
                .build();

        return roomResponseDTO;
    }

    public String getRoomKey(String code){
        return "lingoland" + ":room" + ":" + code;
    }

    private String getLoginIdFromUserDetails(CustomUserDetails customUserDetails){
        return customUserDetails.getUsername();
    }
}
