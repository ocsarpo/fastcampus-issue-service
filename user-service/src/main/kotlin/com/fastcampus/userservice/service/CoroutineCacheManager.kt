package com.fastcampus.userservice.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Component
class CoroutineCacheManager<T> {

    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()

    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(cached = value, ttl = Instant.now().plusMillis(ttl.toMillis()))
    }

    suspend fun awaitEvict(key: String) {
        localCache.remove(key)
    }

    suspend fun awaitGetOrPut(
        key: String,
        ttl: Duration? = Duration.ofMinutes(5),
        supplier: suspend () -> T,
    ): T {
        val now = Instant.now()
        val cacheWrapper = localCache[key]

        val cached = if (cacheWrapper == null) {
            CacheWrapper(cached = supplier(), ttl = now.plusMillis(ttl!!.toMillis())).also {
                localCache[key] = it
            }
        } else if (now.isAfter(cacheWrapper.ttl)) {
            // 캐시 만료된 경우 기존 캐시 삭제, 새로운 캐시 생성
            localCache.remove(key)
            CacheWrapper(cached = supplier(), ttl = now.plusMillis(ttl!!.toMillis())).also {
                localCache[key] = it
            }
        } else {
            // 캐시가 유효한 경우
            cacheWrapper
        }

        checkNotNull(cached.cached)
        return cached.cached
    }

    data class CacheWrapper<T>(
        val cached: T,
        val ttl: Instant
    )
}
