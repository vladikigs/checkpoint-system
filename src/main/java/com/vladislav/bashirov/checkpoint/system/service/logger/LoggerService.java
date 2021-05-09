package com.vladislav.bashirov.checkpoint.system.service.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggerService implements LoggerInterface {

    Logger logger = LogManager.getLogger("TRACKING_USER_ACTION_LOGGER");

    @Override
    public void addLogException(Exception ex) {
        logger.error(ex);
    }

    @Override
    public void addUserAction(String message) {
        logger.info(message);
    }
}
