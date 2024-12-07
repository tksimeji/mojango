package com.tksimeji.mojango.texture

import com.google.gson.JsonObject

class Skin internal constructor(json: JsonObject): Texture(json) {
    /**
     * Get the skin model.
     *
     * @return Model
     */
    fun getModel(): SkinModel {
        return if (json.getAsJsonObject("metadata")?.get("model")?.asString == "slim") SkinModel.SLIM else SkinModel.WIDE
    }

    /**
     * Determine if the skin model is wide.
     *
     * @return True if the model is wide
     */
    fun isWide(): Boolean {
        return getModel() == SkinModel.WIDE
    }

    /**
     * Determine if the skin model is slim.
     *
     * @return True if the model is slim
     */
    fun isSlim(): Boolean {
        return getModel() == SkinModel.SLIM
    }
}