package com.example.gateway.aspect;

import com.example.gateway.common.dto.NotificationsRequestDto.NotificationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackNotification {
    String tableName() default "notifications";
    NotificationType type();
}