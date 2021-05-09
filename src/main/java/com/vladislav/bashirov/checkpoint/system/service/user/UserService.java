package com.vladislav.bashirov.checkpoint.system.service.user;

import com.vladislav.bashirov.checkpoint.system.entity.Room;
import com.vladislav.bashirov.checkpoint.system.entity.User;
import com.vladislav.bashirov.checkpoint.system.repository.UserRepository;
import com.vladislav.bashirov.checkpoint.system.service.logger.LoggerInterface;
import com.vladislav.bashirov.checkpoint.system.service.room.RoomInterface;
import org.springframework.stereotype.Service;

import java.security.AccessControlException;

@Service
public class UserService implements UserInterface {

    private final LoggerInterface loggerService;
    private final UserRepository userRepository;
    private final RoomInterface roomService;

    public UserService(LoggerInterface loggerService, UserRepository userRepository, RoomInterface roomService) {
        this.loggerService = loggerService;
        this.userRepository = userRepository;
        this.roomService = roomService;
    }

    @Override
    public User getUserByKeyId(Long keyId) {
        User user = userRepository.findById(keyId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Not found user");
        }
        return user;
    }

    @Override
    public boolean leavingTheRoom(Long keyId, Long roomId) {
        User user = getUserByKeyId(keyId);
        Room room = roomService.getRoomByRoomId(roomId);

        loggerService.addUserAction("user KeyId=" + keyId + " trying leaving room roomId=" + roomId);

        if (user == null) {
            throw new IllegalArgumentException("Not found user");
        }

        if (user.getRoom() != room) {
            throw new IllegalArgumentException("The user is in the room " +
                    (user.getRoom() == null?"null":user.getRoom().getId()) +
                    " and trying to go out of room " + room.getId());
        }
        loggerService.addUserAction("user KeyId=" + keyId + " success exit from roomId=" + roomId);
        user.setRoom(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean enteringTheRoom(Long keyId, Long roomId) {
        User user = getUserByKeyId(keyId);
        Room room = roomService.getRoomByRoomId(roomId);

        loggerService.addUserAction("user KeyId=" + keyId + " trying enter in roomId=" + roomId);

        if (user.getRoom() != null) {
            throw new IllegalArgumentException("The user is in another room and cannot enter in room " + room.getId());
        }

        if (user.getKeyId() % room.getId() != 0) {
            throw new AccessControlException("User doesn't have access");
        }

        loggerService.addUserAction("user KeyId=" + keyId + " success enter in roomId=" + roomId);

        user.setRoom(room);
        userRepository.save(user);
        return true;
    }

}
