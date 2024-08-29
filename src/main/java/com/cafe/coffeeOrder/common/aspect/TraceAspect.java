package com.cafe.coffeeOrder.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;


@Slf4j
public class TraceAspect {
    @Before("@annotation(com.cafe.coffeeOrder.common.annotation.Trace)")
    public void doTrace(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("[trace] {} args={}", joinPoint.getSignature(), args);
    }
}