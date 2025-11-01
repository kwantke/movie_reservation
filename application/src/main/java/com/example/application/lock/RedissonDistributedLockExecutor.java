package com.example.application.lock;

import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedissonDistributedLockExecutor implements DistributedLockExecutor {

    private final RedissonClient redissonClient;

    @Override
    public <T> T executeWithLock(String key, long waitTime, long leaseTime, Supplier<T> task) {
        RLock lock = redissonClient.getLock(key);
        boolean acquired = false;

        long acquireElapsedNs = 0L;
        try {
            log.info("[락 시도] Key: {}, Thread: {}", key, Thread.currentThread().getId());

            acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);


            if (!acquired) {
                log.warn("[락 획득 실패] Key: {}, Thread: {}", key, Thread.currentThread().getId());
                throw new CustomException(ErrorCode.DISTRIBUTED_LOCK_FAILURE, "좌석 예약 요청이 많아 처리가 지연되었습니다. 다시 시도해주세요.");
            }
            log.info("[락 획득 성공] Key: {}, Thread: {}", key, Thread.currentThread().getId());

            acquireElapsedNs = System.nanoTime(); // 락 획득 시작 시간

            return task.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("[ERROR] Lock acquisition interrupted", e);
        } finally {

            if (acquired && lock.isHeldByCurrentThread()) {
                try {
                    lock.unlock();
                    log.info("[락 해제] Key: {}, Thread: {}", key, Thread.currentThread().getId());
                    long releaseElapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - acquireElapsedNs);
                    log.info("[락 해제 완료] Key: {}, Thread: {}, 락 획득 후 해제까지 걸린시간 {} ms",
                            key, Thread.currentThread().getId(), releaseElapsedMs);
                } catch (IllegalMonitorStateException ex) {
                    log.warn("[락 해제 실패: 강제 해제 시도] Key: {}", key);
                    lock.forceUnlock();
                }
            }
        }
    }

}
