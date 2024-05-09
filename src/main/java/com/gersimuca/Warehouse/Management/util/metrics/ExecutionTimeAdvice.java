package com.gersimuca.Warehouse.Management.util.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAdvice.class);

    @Around("@annotation(com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endtime = System.currentTimeMillis();
        logger.info("{}=>{}. Time taken for Execution is : {}ms", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), (endtime - startTime));
        return object;
    }
}
