package com.example.gateway.aspect;

import com.example.gateway.common.dto.AuditLogsRequestDto;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLoggable {
    String tableName() ;
    AuditLogsRequestDto.AuditAction action();
}
