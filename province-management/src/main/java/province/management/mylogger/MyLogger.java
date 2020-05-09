package province.management.mylogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Aspect
public class MyLogger {
    @AfterThrowing(pointcut = "execution(public * province.management.service.*.*(..))", throwing = "e")
    public void log(JoinPoint joinPoint, Exception e){
        String ClassName = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println(String.format("[CMS] co loi xay ra: %s.%s%s: %s", ClassName, method, args, e.getMessage()));
    }
}
