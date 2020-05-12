package province.management.mylogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class MyLogger {
    @After(value = "execution(public * province.management.controllers.CustomerController.listCustomer(..))")
    public void log(JoinPoint joinPoint){
        String ClassName = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println(String.format("[CMS] co loi xay ra: %s.%s%s: %s", ClassName, method, args));
    }
    @AfterThrowing(pointcut = "execution(public * province.management.service.*.*(..))", throwing = "e")
    public void logController(JoinPoint joinPoint, Exception e){
        String ClassName = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println(String.format("[CMS] co loi xay ra: %s.%s%s: %s", ClassName, method, args, e.getMessage()));
    }
}
