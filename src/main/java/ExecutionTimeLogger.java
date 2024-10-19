import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@Aspect
public class ExecutionTimeLogger {

    // Используем стандартный логгер
    private static final Logger logger = Logger.getLogger(ExecutionTimeLogger.class.getName());

    // Логируем время выполнения метода, помеченного аннотацией @LogExecutionTime
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(@NotNull ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();  // Начало замера времени
        Object proceed = joinPoint.proceed();     // Выполнение целевого метода
        long executionTime = System.currentTimeMillis() - start;  // Разница времени

        // Логирование результата
        logger.info(joinPoint.getSignature() + " executed in " + executionTime + " ms");
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + " ms");
        System.out.println(" executed in " + executionTime + " ms");

        return proceed;  // Возвращаем результат выполнения метода
    }
}
