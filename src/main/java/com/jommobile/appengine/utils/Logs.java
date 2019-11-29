/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.utils;

import java.util.logging.Logger;

/**
 * Created by MAO Hieng on 7/4/17.
 *
 * <p>
 */
public final class Logs {

    private Logs() {
        // no instance
    }

    public static Logger makeLogger(Class<?> tClass) {
        return Logger.getLogger("jom-" + tClass.getSimpleName());
    }

//    /**
//     * @deprecated because it point to this class code line, not any line that calls this method.
//     */
//    @Deprecated
//    public static void info(Logger logger, String msg) {
//        logger.info(msg);
//    }
//
//    @Deprecated
//    public static void warning(Logger logger, String msg) {
//        logger.warning(msg);
//    }
}
