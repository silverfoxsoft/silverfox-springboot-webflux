package com.silverfox.publicsvr.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <br>日志切面</br>
 *
 * @author fattyca1@qq.com
 * @since 2022/8/10
 */
@Aspect
@Configuration
@Slf4j
public class LoggerAspect {
    private final String EXECUTION = "execution(* com.silverfox.publicsvr.*.presentation.*.*(..))";
    @Pointcut(EXECUTION)
    public void logPointCut() {

    }
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        var result = joinPoint.proceed();
        if (result instanceof Mono) {
            return logMonoResult(joinPoint, start, (Mono) result);
        } else if (result instanceof Flux) {
            return logFluxResult(joinPoint, start, (Flux) result);
        } else {
            logResult(joinPoint, start, result);
            return result;
        }

    }
    private Mono logMonoResult(ProceedingJoinPoint joinPoint, long start, Mono result) {
        AtomicReference<String> traceId = new AtomicReference<>("");
        return result
                .doOnSuccess(o -> {
                    setTraceIdInMDC(traceId);
                    var response = Objects.nonNull(o) ? o.toString() : "";
                    logEntry(joinPoint);
                    logSuccessExit(joinPoint, start, response);
                })
                .contextWrite(context -> {
                    setTraceIdFromContext(traceId, (Context) context);
                    return context;
                })
                .doOnError(o -> {
                    setTraceIdInMDC(traceId);
                    logEntry(joinPoint);
                    logErrorExit(joinPoint, start, o.toString());
                });
    }

    private Flux logFluxResult(ProceedingJoinPoint joinPoint, long start, Flux result) {
        return result
                .doFinally(o -> {
                    logEntry(joinPoint);
                    logSuccessExit(joinPoint, start, o.toString()); // NOTE: this is costly
                })
                .doOnError(o -> {
                    logEntry(joinPoint);
                    logErrorExit(joinPoint, start, o.toString());
                });
    }

    private void logResult(ProceedingJoinPoint joinPoint, long start, Object result) {
        try {
            logEntry(joinPoint);
            logSuccessExit(joinPoint, start, result.toString());
        } catch (Exception e) {
            logEntry(joinPoint);
            logErrorExit(joinPoint, start, e.getMessage());
        }
    }


    private void logErrorExit(ProceedingJoinPoint joinPoint, long start, String error) {
        log.error("Exit: {}.{}() had arguments = {}, with result = {}, Execution time = {} ms",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                joinPoint.getArgs() == null || joinPoint.getArgs().length == 0 ? "" : joinPoint.getArgs()[0],
                error, (System.currentTimeMillis() - start));
    }

    private void logSuccessExit(ProceedingJoinPoint joinPoint, long start, String response) {
        log.info("Response: {}.{}()  args = {}, result = {}, Cost time = {} ms",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                joinPoint.getArgs() == null || joinPoint.getArgs().length == 0 ? "" : joinPoint.getArgs()[0],
                response, (System.currentTimeMillis() - start));
    }

    private void logEntry(ProceedingJoinPoint joinPoint) {
        log.info("Request: {}.{}() args = {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                joinPoint.getArgs() == null || joinPoint.getArgs().length == 0 ? "" : joinPoint.getArgs()[0]);
    }

    private void setTraceIdFromContext(AtomicReference<String> traceId, Context context) {
        if (context.hasKey(ConstantsFields.TRACE_ID_KEY)) {
            traceId.set(context.get(ConstantsFields.TRACE_ID_KEY));
            setTraceIdInMDC(traceId);
        }
    }

    private void setTraceIdInMDC(AtomicReference<String> traceId) {
        if (!traceId.get().isEmpty()) {
            MDC.put(ConstantsFields.MDC_TRACE_ID_KEY, traceId.get());
        }
    }

}
