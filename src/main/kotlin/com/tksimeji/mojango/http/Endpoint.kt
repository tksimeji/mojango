package com.tksimeji.mojango.http

private object Server {
    const val API_SERVER = "https://api.mojang.com"
    const val SESSION_SERVER = "https://sessionserver.mojang.com"
}

internal enum class Endpoint(private val url: String) {
    MINECRAFT_NAME(Server.API_SERVER + "/users/profiles/minecraft/\${name}"),
    MINECRAFT_PROFILE(Server.SESSION_SERVER + "/session/minecraft/profile/\${id}");

    fun url(vararg args: String): String {
        val map = args.asSequence()
            .map { it.split('=') }
            .filter { it.size == 2 }
            .associate { it[0] to it[1] }

        var url = url

        map.forEach{ (k, v) -> url = url.replace("\${$k}", v) }

        return url
    }

    override fun toString(): String {
        return url
    }
}