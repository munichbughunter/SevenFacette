package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.utils.WrongDimensionException
import java.awt.image.BufferedImage




/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
object WebImageTools {

    /**
     * Computes the percentage of difference between 2 JPEG images of the same size.
     * Images must have the same dimensions.
     *
     * @param first  first image to compare
     * @param second second image to compare
     * @return difference in percents between original and provided WebImages
     */
    fun compareTwoImages(first: WebImage, second: WebImage): Double {
        return compareTwoImages(first.getRawImage(), second.getRawImage())
    }

    private fun compareTwoImages(first: BufferedImage?, second: BufferedImage?): Double {
        val width = first!!.width
        val height = first.height
        val width2 = second!!.width
        val height2 = second.height
        if (width != width2 || height != height2) {
            throw WrongDimensionException(String.format("Compared images must have the same dimensions: " +
                    "(%d,%d) vs. (%d,%d)", width, height, width2, height2))
        }
        var diff: Long = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                diff += pixelDifference(first.getRGB(x, y), second.getRGB(x, y)).toLong()
            }
        }
        val maxDiff = 3L * 255 * width * height
        return 100.0 * diff / maxDiff
    }

    fun exactlyTheSame(first: WebImage, second: WebImage): Boolean {
        return equalWithinDeviation(first, second, 0.0)
    }

    fun equalWithinDeviation(first: WebImage, second: WebImage, allowedDeviation: Double): Boolean {
        return try {
            val difference = compareTwoImages(first, second)
            if (difference <= allowedDeviation) true else false
        } catch (e: WrongDimensionException) { //thrown when dimensions are different
            false
        }
    }

    private fun pixelDifference(rgb1: Int, rgb2: Int): Int {
        val r1 = rgb1 shr 16 and 0xff
        val g1 = rgb1 shr 8 and 0xff
        val b1 = rgb1 and 0xff
        val r2 = rgb2 shr 16 and 0xff
        val g2 = rgb2 shr 8 and 0xff
        val b2 = rgb2 and 0xff
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2)
    }
}
