package com.vladislav.bashirov.checkpoint.system.controller;

import com.vladislav.bashirov.checkpoint.system.service.logger.LoggerInterface;
import com.vladislav.bashirov.checkpoint.system.service.user.UserInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.AccessControlException;

@Controller
public class ScannerController {

    private final UserInterface userService;
    private final LoggerInterface loggerService;

    public ScannerController(UserInterface userService, LoggerInterface loggerService) {
        this.userService = userService;
        this.loggerService = loggerService;
    }

    @GetMapping("/check")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public boolean entranceToTheRoom(@RequestParam("roomId") long roomId,
                                  @RequestParam("entrance") boolean entrance,
                                  @RequestParam("keyId") long keyId) {

        if (entrance) {
            return userService.enteringTheRoom(keyId, roomId);
        } else {
            return userService.leavingTheRoom(keyId, roomId);
        }

    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleException(IllegalArgumentException ex) {
        loggerService.addLogException(ex);
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ex.getMessage(), internalServerError);
    }

    @ExceptionHandler({ AccessControlException.class })
    public ResponseEntity<Object> handleException(AccessControlException ex) {
        loggerService.addLogException(ex);
        HttpStatus internalServerError = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(ex.getMessage(), internalServerError);
    }
}
