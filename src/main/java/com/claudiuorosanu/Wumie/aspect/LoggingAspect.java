package com.claudiuorosanu.Wumie.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Services
    @Before("execution(* com.claudiuorosanu.Wumie.service.*.*(..))")
    public void beforeServiceMethodAdvice(JoinPoint joinPoint){
        log.info(
            "Calling method on service...\n service: {}... method: {}... args: {}",
            joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs()
        );
    }

    // Controllers
    @Before("execution(* com.claudiuorosanu.Wumie.controller.*.*(..))")
    public void beforeControllerMethodAdvice(JoinPoint joinPoint){
        log.info(
            "Calling method on controller...\n controller: {}... method: {}... args: {}",
            joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs()
        );
    }

    // Database seeder
    @Before("execution(* com.claudiuorosanu.Wumie.DatabaseSeeder.*(..))")
    public void beforeDatabaseSeederMethodAdvice(JoinPoint joinPoint){
        log.info(
            "Seeding database...",
            joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs()
        );
    }

    // Converters
    @Before("execution(* com.claudiuorosanu.Wumie.converter.*.convert(..))")
    public void beforeConvertedMethodAdvice(JoinPoint joinPoint){
        log.info(
            "Converting...\n converter: {}... method: {}... args: {}",
            joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs()
        );
    }

    // Security
    @Before("execution(* com.claudiuorosanu.Wumie.security.JwtTokenProvider.generateToken(..))")
    public void beforeJWTTokenGenerationAdvice(JoinPoint joinPoint) {
        log.info("Generating JWT token...");
    }

    @AfterReturning(
        value = "execution(* com.claudiuorosanu.Wumie.security.JwtTokenProvider.generateToken(..))",
        returning = "jwtToken"
    )
    public void afterJWTTokenGenerationAdvice(JoinPoint joinPoint, String jwtToken) {
        log.info("JWT token generated... {}", jwtToken);
    }


    @Before("execution(* com.claudiuorosanu.Wumie.security.JwtTokenProvider.getUserIdFromJWT(..))")
    public void beforeGettingUserFromJwtTokenAdvice(JoinPoint joinPoint) {
        log.info("Getting user id from JWT token... jwt = {}", joinPoint.getArgs());
    }

    @AfterReturning(
            value = "execution(* com.claudiuorosanu.Wumie.security.JwtTokenProvider.getUserIdFromJWT(..))",
            returning = "userId"
    )
    public void afterJWTTokenGenerationAdvice(JoinPoint joinPoint, Long userId) {
        log.info("User id retrieved from JWT token... user id = {}", userId);
    }

}
