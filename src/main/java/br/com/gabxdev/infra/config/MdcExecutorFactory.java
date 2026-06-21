package br.com.gabxdev.infra.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Configuration
public class MdcExecutorFactory {

    @Bean
    public ExecutorService virtualThreadPerTaskExecutor() {
        return new DelegatingExecutorService(
                Executors.newVirtualThreadPerTaskExecutor()
        );
    }

    private static class DelegatingExecutorService extends AbstractExecutorService {

        private final ExecutorService delegate;

        DelegatingExecutorService(ExecutorService delegate) {
            this.delegate = delegate;
        }

        @Override
        public void execute(Runnable command) {
            Map<String, String> context = MDC.getCopyOfContextMap();

            delegate.execute(() -> {
                if (context != null) {
                    MDC.setContextMap(context);
                }

                try {
                    command.run();
                } finally {
                    MDC.clear();
                }
            });
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            Map<String, String> context = MDC.getCopyOfContextMap();

            return delegate.submit(() -> {
                if (context != null) {
                    MDC.setContextMap(context);
                }

                try {
                    return task.call();
                } finally {
                    MDC.clear();
                }
            });
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(task);
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return super.submit(task, result);
        }

        // delegações restantes...

        @Override
        public void shutdown() {
            delegate.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return delegate.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return delegate.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return delegate.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit)
                throws InterruptedException {
            return delegate.awaitTermination(timeout, unit);
        }
    }
}