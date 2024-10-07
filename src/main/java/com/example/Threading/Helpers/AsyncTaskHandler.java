package com.example.Threading.Helpers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class AsyncTaskHandler {
    @Async("taskExecutor")
    public <T> CompletableFuture<T> executeAsync(Supplier<T> task) {
        try {
            T result = task.get();
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
