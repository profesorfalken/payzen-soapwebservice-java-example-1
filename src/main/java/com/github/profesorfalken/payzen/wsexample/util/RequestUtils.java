package com.github.profesorfalken.payzen.wsexample.util;

/**
 * Class that contains utility methods to handle request data
 * 
 * @author Javier Garcia Alonso
 */
public  class RequestUtils {
    public static Long getLongFromRequest(String param) {
        if (param != null) {
            return Long.valueOf(param);
        }
        return null;
    }

    public static Integer getIntegerFromRequest(String param) {
        if (param != null) {
            return Integer.valueOf(param);
        }
        return null;
    }
}
