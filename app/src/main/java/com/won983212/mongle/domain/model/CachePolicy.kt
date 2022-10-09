package com.won983212.mongle.domain.model

enum class CachePolicy {
    /** **기본값.** 앱 시작후 최초 1회, 무조건 Remote에서 요청을 받아 캐싱한다. 이후에 계속 캐싱된 데이터를 반환. */
    ONCE,

    /** 데이터가 이미 캐싱되어있다면 그 데이터를 반환하고, 아니라면 Remote에 요청후 받은 데이터를 캐싱&반환한다. */
    GET_OR_FETCH,

    /** 캐싱하지 않는다. 캐싱된 데이터가 있든 없든, 무조건 Remote에서 요청해서 데이터를 반환한다. 이때 요청해서 받은 데이터는 캐싱되지 않는다. */
    NEVER,

    /** 무조건 fetch & caching 한다. 캐싱된 데이터가 있든 없든, Remote에 요청하고 캐싱&반환한다.*/
    REFRESH;

    suspend fun <T> get(resource: CacheableResource<T>): Result<T> {
        when (this) {
            ONCE -> {
                val resourceName = resource.getResourceName()
                if (!FETCHED_RESOURCES.contains(resourceName.ordinal)) {
                    FETCHED_RESOURCES.add(resourceName.ordinal)
                    return fetchAndCache(resource)
                }
                return getOrFetchAndCache(resource)
            }
            GET_OR_FETCH -> {
                return getOrFetchAndCache(resource)
            }
            NEVER -> {
                return resource.fetch()
            }
            REFRESH -> {
                return fetchAndCache(resource)
            }
        }
    }

    private suspend fun <T> getOrFetchAndCache(resource: CacheableResource<T>): Result<T> {
        val cache = resource.loadFromCache()
        cache.onSuccess {
            return cache
        }
        return fetchAndCache(resource)
    }

    private suspend fun <T> fetchAndCache(resource: CacheableResource<T>): Result<T> {
        return resource.fetch().also {
            it.onSuccess { data ->
                resource.saveCallResult(data)
            }
        }
    }

    companion object {
        @JvmStatic
        val DEFAULT = ONCE

        @JvmStatic
        val FETCHED_RESOURCES = mutableSetOf<Int>()
    }

    interface CacheableResource<T> {
        suspend fun loadFromCache(): Result<T>
        suspend fun saveCallResult(value: T)
        suspend fun fetch(): Result<T>
        fun getResourceName(): ResourceName
    }
}