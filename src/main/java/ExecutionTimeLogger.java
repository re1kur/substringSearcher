import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import java.util.logging.Logger;

/**
 * Aspect class to handle annotations @LogExecutionTime
 */
@Aspect
public class ExecutionTimeLogger {
    private static final Logger logger = Logger.getLogger(ExecutionTimeLogger.class.getName());

    /**
     * @param joinPoint action to start method
     * @return result of executing method
     * @throws Throwable error
     */
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(@NotNull ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();  // Начало замера времени
        Object proceed = joinPoint.proceed();     // Выполнение целевого метода
        long executionTime = System.currentTimeMillis() - start;  // Разница времени
        logger.info(joinPoint.getSignature() + " executed in " + executionTime + " ms");
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + " ms");
        System.out.println(" executed in " + executionTime + " ms");
        return proceed;
    }
}
