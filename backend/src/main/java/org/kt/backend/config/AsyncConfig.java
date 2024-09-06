package org.kt.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 스레드 풀의 기본 크기
        executor.setMaxPoolSize(20); // 스레드 풀의 최대 크기
        executor.setQueueCapacity(500); // 큐의 용량
        executor.setThreadNamePrefix("EmailAnalysis-");
        executor.initialize();
        return executor;
    }
}
