package com.example.gateway.usercontext;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {
    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> usernameHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> rolesHolder = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        userIdHolder.set(userId);
    }
    public static Long getUserId() {
        return userIdHolder.get();
    }

    public static void setUsername(String username) {
        usernameHolder.set(username);
    }

    public static String getUsername() {
        return usernameHolder.get();
    }

    public static void setRoles(String roles) {
        rolesHolder.set(roles);
    }

    public static String getRoles() {
        return rolesHolder.get();
    }

    public static void clear() {
        userIdHolder.remove();
        usernameHolder.remove();
        rolesHolder.remove();
    }

}
