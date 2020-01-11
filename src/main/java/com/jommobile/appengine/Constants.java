package com.jommobile.appengine;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Created by MAO Hieng on 9/26/18.
 * <p>
 *
 * </p>
 */
public class Constants {

    private static Environment ENVIRONMENT;
    public static final boolean LOCAL_DEV;

    static {
        SystemProperty.Environment.Value environmentValue = SystemProperty.environment.value();
        if (environmentValue == SystemProperty.Environment.Value.Development) {
            LOCAL_DEV = true;
            ENVIRONMENT = Environment.LOCAL;
        } else {
            LOCAL_DEV = false;
            ENVIRONMENT = Environment.PRODUCTION;
        }
    }

    public static Environment getAppEngineEnvironment() {
        return ENVIRONMENT;
    }

    public static synchronized void initAppEngineEnvironment(Environment env) {
        ENVIRONMENT = env;
    }

}
