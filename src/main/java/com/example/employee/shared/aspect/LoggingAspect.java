package com.example.employee.shared.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(com.example.employee..controller..*)")
    public void controllerLayer() {}

    @Around("controllerLayer()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes())
                        .getRequest();

        String httpMethod = request.getMethod();

        Object[] args = joinPoint.getArgs();

        // 🔹 Hide ID for READ operations
        Object[] loggedArgs = isReadOperation(httpMethod)
                ? maskIds(args)
                : args;

        log.info("➡️  {} {} | {}() | args={}",
                httpMethod,
                request.getRequestURI(),
                joinPoint.getSignature().toShortString(),
                Arrays.toString(loggedArgs));

        Object result = joinPoint.proceed();

        log.info("⬅️  {} {} | {}() completed",
                httpMethod,
                request.getRequestURI(),
                joinPoint.getSignature().toShortString());

        return result;
    }

    @Pointcut("within(com.example.employee..service..*)")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        try {
            // DEBUG → arguments (masked automatically by controller layer)
            if (log.isDebugEnabled()) {
                log.debug("▶️  Service START: {}() | args={}",
                        joinPoint.getSignature().toShortString(),
                        Arrays.toString(joinPoint.getArgs()));
            } else {
                log.info("▶️  Service START: {}()",
                        joinPoint.getSignature().toShortString());
            }

            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - startTime;

            log.info("✔️  Service END: {}() | time={} ms",
                    joinPoint.getSignature().toShortString(),
                    duration);

            return result;

        } catch (Exception ex) {

            long duration = System.currentTimeMillis() - startTime;

            log.error("❌ Service ERROR: {}() | time={} ms | msg={}",
                    joinPoint.getSignature().toShortString(),
                    duration,
                    ex.getMessage());

            throw ex;
        }
    }


    /* ==============================
       HELPERS
       ============================== */

    private boolean isReadOperation(String httpMethod) {
        return "GET".equalsIgnoreCase(httpMethod);
    }

    private Object[] maskIds(Object[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof Long || arg instanceof Integer || arg instanceof String) {
                        return "***";
                    }
                    return arg;
                })
                .toArray();
    }
}
