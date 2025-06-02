package com.yshs.jeiae2compact.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志工具类，用于统一日志输出格式。
 */
public class ModLogger {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message, Throwable e) {
        LOGGER.error(message, e);
    }
}