package com.global.aspects;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
	

	@Around(value = "execution(* com.global..*.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info(joinPoint.getSignature().toString() + " method execution started ");
		Instant start = Instant.now() ; 
		Object obj = joinPoint.proceed() ;
		Instant end = Instant.now() ; 
		Long timeElapsed = Duration.between(start, end).toMillis() ; 
		log.info("time took to execute " + joinPoint.getSignature().toString() + " method is : " + timeElapsed);
		log.info(joinPoint.getSignature().toString() + " method execution ended ");
		return obj ; 
	}
	@AfterThrowing(value = "execution(* com.global..*.*(..))" , throwing = "ex")
	public void logException(JoinPoint joinPoint , Exception ex) {
		log.error(joinPoint.getSignature().toString() + " have this exception " + ex.getMessage());
	}
	
	
}
