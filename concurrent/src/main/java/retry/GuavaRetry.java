package retry;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaRetry {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaRetry.class);

    public static void main(String[] args) throws ExecutionException, RetryException {
        retryDemo();
    }

    private static void retryDemo() throws ExecutionException, RetryException {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
//                .retryIfException()
                .retryIfRuntimeException()
                /*.retryIfExceptionOfType(Exception.class)
                .retryIfResult(Predicates.equalTo(false))*/
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(6))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        LOGGER.info("retry:" + attempt.getAttemptNumber());
                    }
                }).build();


        retryer.call(new Callable<Boolean>() {

            int times = 1;

            @Override
            public Boolean call() throws Exception {
                LOGGER.info("call times={}", times);
                times++;

                if (times == 1) {
                    return true;
                }
                if (times == 2) {
                    throw new NullPointerException();
                } else if (times == 3) {
                    throw new Exception();
                } else if (times == 4) {
                    throw new RuntimeException();
                } else if (times == 5) {
                    return false;
                } else {
                    return true;
                }
            }
        });


    }

}
