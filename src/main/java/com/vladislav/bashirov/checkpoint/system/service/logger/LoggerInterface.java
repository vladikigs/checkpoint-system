package com.vladislav.bashirov.checkpoint.system.service.logger;

public interface LoggerInterface {

    void addLogException(Exception ex);
    void addUserAction(String message);

}
