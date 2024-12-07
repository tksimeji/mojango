package com.tksimeji.mojango.http

import com.tksimeji.mojango.Mojango

internal interface Response {
    fun getUrl(): String

    fun getTimestamp(): Long

    fun isActive(): Boolean {
        return System.currentTimeMillis() - getTimestamp() <= Mojango.getTtl()
    }
}