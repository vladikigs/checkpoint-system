package com.vladislav.bashirov.checkpoint.system.service.db;

import com.vladislav.bashirov.checkpoint.system.entity.Room;
import com.vladislav.bashirov.checkpoint.system.entity.User;
import com.vladislav.bashirov.checkpoint.system.repository.RoomRepository;
import com.vladislav.bashirov.checkpoint.system.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FillingDatabaseService implements FillingDatabase{

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @PostConstruct
    public void init() {
        fillingInitDatabaseData();
    }

    public FillingDatabaseService(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void fillingInitDatabaseData() {
        for (int i = 1; i <= 10000; i++) {
            User user = new User(null, "login-" + i);
            userRepository.addUser(user.getKeyId(), user.getLogin());
        }

        for (int i = 1; i <= 5; i++) {
            Room room = new Room((long) i);
            roomRepository.save(room);
        }
    }
}
