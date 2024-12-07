package com.tksimeji.mojango.texture

import java.awt.image.BufferedImage

interface ITexture {
    /**
     * Get the url of the texture image.
     *
     * @return Texture URL
     */
    fun getUrl(): String

    /**
     * Get the texture as an image.
     *
     * @return Image
     */
    fun asImage(): BufferedImage
}