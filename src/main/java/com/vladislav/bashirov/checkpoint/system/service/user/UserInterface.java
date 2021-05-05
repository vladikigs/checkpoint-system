package com.vladislav.bashirov.checkpoint.system.service.user;

import com.vladislav.bashirov.checkpoint.system.entity.User;

public interface UserInterface {

    User getUserByKeyId(Long keyId);

    boolean leavingTheRoom(Long keyId, Long roomId);

    boolean enteringTheRoom(Long keyId, Long roomId);

}
