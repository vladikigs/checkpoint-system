package com.vladislav.bashirov.checkpoint.system.service;

import com.vladislav.bashirov.checkpoint.system.entity.Room;
import com.vladislav.bashirov.checkpoint.system.entity.User;
import com.vladislav.bashirov.checkpoint.system.repository.RoomRepository;
import com.vladislav.bashirov.checkpoint.system.repository.UserRepository;
import com.vladislav.bashirov.checkpoint.system.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoomRepository roomRepository;

    // (6/2)=true
    @Test
    void enteringTheRoom() {
        User user = new User(6L, "testUser");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(6L);

        Room room = new Room(2L);
        Mockito.doReturn(Optional.of(room)).when(roomRepository).findById(2L);

        boolean b = userService.enteringTheRoom(6L, 2L);
        assertTrue(b);
    }

    // (2/4)=false
    @Test
    void enteringTheRoomExceptionNotAccess() {
        User user = new User(2L, "testUser");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(2L);

        Room room = new Room(4L);
        Mockito.doReturn(Optional.of(room)).when(roomRepository).findById(4L);

        Exception exception = assertThrows(AccessControlException.class, () -> {
            userService.enteringTheRoom(2L, 4L);
        });
    }

    //
    @Test
    void enteringTheRoomExceptionUserIsInAnotherRoom() {
        Room room = new Room(2L);
        Mockito.doReturn(Optional.of(room)).when(roomRepository).findById(2L);

        User user = new User(6L, "testUser");
        user.setRoom(new Room(1L));
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(6L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.enteringTheRoom(6L, 2L);
        });
    }

}