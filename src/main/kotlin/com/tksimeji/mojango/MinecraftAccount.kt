package com.tksimeji.mojango

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tksimeji.mojango.http.Endpoint
import com.tksimeji.mojango.http.MojangAPI
import com.tksimeji.mojango.texture.Cape
import com.tksimeji.mojango.texture.Skin
import com.tksimeji.mojango.texture.Texture
import java.util.Base64
import java.util.UUID

class MinecraftAccount internal constructor(private val uuid: UUID) {
    companion object {
        internal val instances: MutableSet<MinecraftAccount> = mutableSetOf()
    }

    init {
        instances.add(this)
    }

    /**
     * Get the ID, that is the UUID with the hyphens removed.
     *
     * @return Account ID
     */
    fun getId(): String {
        return getUniqueId().toString().replace("-", "")
    }

    /**
     * Get the UUID.
     *
     * @return Account Unique ID
     */
    fun getUniqueId(): UUID {
        return uuid
    }

    /**
     * Get the name.
     *
     * @return Account name
     */
    fun getName(): String {
        return MojangAPI.fetch(Endpoint.MINECRAFT_PROFILE.url("id=${getId()}")).asJsonObject.get("name").asString
    }

    /**
     * Get the skin, but if not set, returns null.
     *
     * @return Skin
     */
    fun getSkin(): Skin? = getTextures().getAsJsonObject("SKIN")?.let { Texture.skin(it) }

    /**
     * Get the skin, but if not set or not owned, returns null.
     *
     * @return Cape
     */
    fun getCape(): Cape? = getTextures().getAsJsonObject("CAPE")?.let { Texture.cape(it) }

    private fun getTextures(): JsonObject {
        val value = MojangAPI.fetch(Endpoint.MINECRAFT_PROFILE.url("id=${getId()}"))
            .asJsonObject
            .getAsJsonArray("properties").first()
            .asJsonObject.get("value").asString

        return JsonParser.parseString(String(Base64.getDecoder().decode(value))).asJsonObject.getAsJsonObject("textures")
    }
}