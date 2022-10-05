package com.won983212.mongle.data.util

enum class CachePolicy {
    /** **기본값.** 데이터가 이미 캐싱되어있다면 그 데이터를 반환하고, 아니라면 Remote에 요청후 받은 데이터를 캐싱&반환한다. */
    GET_OR_CACHE,

    /** 캐싱하지 않는다. 캐싱된 데이터가 있든 없든, 무조건 Remote에서 요청해서 데이터를 반환한다. 이때 요청해서 받은 데이터는 캐싱되지 않는다. */
    NEVER,

    /** 무조건 fetch & caching 한다. 캐싱된 데이터가 있든 없든, Remote에 요청하고 캐싱&반환한다.*/
    REFRESH;

    suspend fun <T> get(resource: CacheableResource<T>): Result<T> {
        when (this) {
            GET_OR_CACHE -> {
                val cache = resource.loadFromCache()
                if (cache.isSuccess) {
                    return cache
                }
                return resource.fetch().also {
                    it.onSuccess { data ->
                        resource.saveCallResult(data)
                    }
                }
            }
            NEVER -> {
                return resource.fetch()
            }
            REFRESH -> {
                return resource.fetch().also {
                    it.onSuccess { data ->
                        resource.saveCallResult(data)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        val DEFAULT = GET_OR_CACHE
    }

    interface CacheableResource<T> {
        suspend fun loadFromCache(): Result<T>
        suspend fun saveCallResult(value: T)
        suspend fun fetch(): Result<T>
    }
}