package com.pelindo.ebtos.security;


/**
 *
 * @author R. Seno Anggoro A
 */
public class UserUtil {
    private static ThreadLocal<String> currentUser = new ThreadLocal<String>();

    public static void setCurrentUser(String user) {
        currentUser.set(user);
    }

    public static String getCurrentUser() {
        return currentUser.get();
    }
}
