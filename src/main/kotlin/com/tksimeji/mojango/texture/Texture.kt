package com.tksimeji.mojango.texture

import com.google.gson.JsonObject
import java.awt.image.BufferedImage
import java.net.URI
import javax.imageio.ImageIO
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

abstract class Texture(protected val json: JsonObject): ITexture {
    companion object {
        private val instances: MutableSet<Texture> = mutableSetOf()

        fun skin(json: JsonObject): Skin {
            return create(json, Skin::class)
        }

        fun cape(json: JsonObject): Cape {
            return create(json, Cape::class)
        }

        private inline fun <reified T: Texture> create(json: JsonObject, clazz: KClass<out T>): T {
            val url = json.get("url").asString
            val existingInstance = instances.find { it.getUrl() == url && it is T } as? T
            existingInstance?.let { return it }

            val constructor = clazz.primaryConstructor.also { it!!.isAccessible = true }
            val newInstance = constructor!!.call(json).also { instances.add(it) }
            return newInstance
        }
    }

    override fun getUrl(): String {
        return json.get("url").asString
    }

    override fun asImage(): BufferedImage {
        return URI(getUrl()).toURL().openStream().use { stream ->
            ImageIO.read(stream)
        }
    }
}