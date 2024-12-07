package com.tksimeji.mojango.http

import com.google.gson.JsonElement
import com.tksimeji.mojango.Mojango
import okhttp3.OkHttpClient
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal object MojangAPI {
    const val API_RATE = 60

    private var requestCount = 0

    private val executor = Executors.newSingleThreadScheduledExecutor()

    private val cache: MutableSet<JsonResponse> = mutableSetOf()

    internal val client = OkHttpClient()

    init {
        executor.scheduleAtFixedRate({ requestCount = 0 }, 1, 1, TimeUnit.MINUTES)
    }

    internal fun fetch(url: String): JsonElement {
        val normalizedUrl = normalize(url)
        val cache = this.cache.firstOrNull { it.getUrl() == normalizedUrl }

        if (cache != null && (cache.isActive() || Mojango.getRateLimit() < requestCount)) {
            return cache.getJsonElement()
        }

        cache?.let { this.cache.remove(it) }

        val response = JsonResponse(normalizedUrl).also { this.cache.add(it); requestCount ++ }
        return response.getJsonElement()
    }

    private fun normalize(url: String): String {
        val uri = URI(url.lowercase())
        return uri.normalize().toString()
    }
}