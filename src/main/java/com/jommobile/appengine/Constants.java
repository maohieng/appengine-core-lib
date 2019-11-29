package com.jommobile.appengine;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Created by MAO Hieng on 9/26/18.
 * <p>
 *
 * </p>
 */
public class Constants {

    public static final boolean LOCAL_DEV;

    static {
        SystemProperty.Environment.Value environmentValue = SystemProperty.environment.value();
        LOCAL_DEV = environmentValue == SystemProperty.Environment.Value.Development;
    }

}
