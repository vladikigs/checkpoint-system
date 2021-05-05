package com.vladislav.bashirov.checkpoint.system.service.room;

import com.vladislav.bashirov.checkpoint.system.entity.Room;
import com.vladislav.bashirov.checkpoint.system.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements RoomInterface{

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room getRoomByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            throw new IllegalArgumentException("Not found room");
        }
        return room;
    }
}
