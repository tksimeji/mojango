package com.tksimeji.mojango

import com.tksimeji.mojango.http.Endpoint
import com.tksimeji.mojango.http.MojangAPI
import java.util.UUID

object Mojango {
    private var ttl = (1000 * 60 * 5).toLong()
    private var rateLimit = MojangAPI.API_RATE

    /**
     * Get the Time To Live in milliseconds.
     *
     * @return Time To Live
     */
    fun getTtl(): Long {
        return ttl
    }

    /**
     * Set the Time To Live, must be specified in milliseconds.
     *
     * @param ttl Time To Live
     */
    fun setTtl(ttl: Long) {
        this.ttl = ttl
    }

    /**
     * Get the request limit per minute.
     *
     * @return Rate Limit
     */
    fun getRateLimit(): Int {
        return rateLimit
    }

    /**
     * Set a limit on requests per minute.
     *
     * @param rateLimit Rate Limit
     */
    fun setRateLimit(rateLimit: Int) {
        this.rateLimit = rateLimit
    }

    /**
     * Get Minecraft account from UUID
     *
     * @param uuid Account UUID
     * @return Minecraft account
     */
    fun account(uuid: UUID): MinecraftAccount {
        return MinecraftAccount.instances
            .find { it.getUniqueId() == uuid }
            ?: MinecraftAccount(uuid)
    }

    /**
     * Get Minecraft account from name
     *
     * @param name Account name
     * @return Minecraft account
     */
    fun account(name: String): MinecraftAccount {
        return account(parseId(MojangAPI.fetch(Endpoint.MINECRAFT_NAME.url("name=$name")).asJsonObject.get("id").asString))
    }

    private fun parseId(id: String): UUID {
        return UUID.fromString(id.substring(0, 8) + '-' +
                id.substring(8, 12) + '-' +
                id.substring(12, 16) + '-' +
                id.substring(16, 20) + '-' +
                id.substring(20))
    }
}