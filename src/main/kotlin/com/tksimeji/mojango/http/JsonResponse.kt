package com.tksimeji.mojango.http

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.Request

internal class JsonResponse(private val url: String): Response {
    private val json = MojangAPI.client.newCall(Request.Builder().url(url).build())
        .execute().use { response ->
            if (! response.isSuccessful || response.body == null) {
                null
            } else {
                JsonParser.parseString(response.body?.string())
            }
        }

    private val timestamp = System.currentTimeMillis()

    override fun getUrl(): String {
        return url
    }

    override fun getTimestamp(): Long {
        return timestamp
    }

    fun getJsonElement(): JsonElement {
        return json!!
    }
}