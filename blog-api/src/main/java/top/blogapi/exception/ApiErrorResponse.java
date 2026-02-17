package top.blogapi.exception;

import java.time.LocalDateTime;
import java.util.Map;


public record ApiErrorResponse(
        String code,
        String message,
        int status,
        String path,
        String traceId,
        LocalDateTime timestamp,
        Map<String, Object> details
) {}