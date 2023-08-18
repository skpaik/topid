package io.goribco.apis.helper;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import jakarta.validation.constraints.Email;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
public class AsyncHelper {
    public static long sendEmailAsync(@Email String email) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPool);

        ListenableFuture<Long> guavaFuture = (ListenableFuture<Long>) service.submit(() ->
                System.out.println("POP")
        );

        long result = guavaFuture.get();

        return result;
    }
}
