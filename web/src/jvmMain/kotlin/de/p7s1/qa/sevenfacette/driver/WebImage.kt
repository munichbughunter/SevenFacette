package de.p7s1.qa.sevenfacette.driver

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class WebImage () {

    private var link: String? = null
    private var url: URL? = null
    private var isBroken = false
    private var image: BufferedImage? = null
    private var requiresProfile = false

    fun WebImage(link: String?) {
        this.link = link
        requiresProfile = false
        try {
            url = URL(this.link)
            image = ImageIO.read(url)
            isBroken = false
        } catch (e: IOException) {
        }
    }

    fun isBroken(): Boolean {
        return isBroken
    }

    fun getLink(): String? {
        return link
    }

    fun getUrl(): URL? {
        return url
    }

    fun getRawImage(): BufferedImage? {
        return image
    }

    /**
     * Computes the percentage of difference between 2 JPEG images of the same size.
     * Images must have the same dimensions.
     *
     * @param image image to compare with
     * @return difference in percents between original and provided WebImages
     */
    fun compareWithImage(image: WebImage?): Double {
        return WebImageTools.compareTwoImages(this, image!!)
    }

    fun isRequireProfile(): Boolean {
        return requiresProfile
    }
}
