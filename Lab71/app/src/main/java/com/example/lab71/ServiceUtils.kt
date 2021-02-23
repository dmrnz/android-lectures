package com.example.lab71

import android.app.Service
import android.os.FileUtils
import com.example.lab71.SupportedImageType.*
import java.io.File
import java.net.URL

@Synchronized
fun saveImage(
    service: Service,
    dir: File,
    url: String,
): String? {

    val imageType = when {
        url.contains(""".${PNG.extension}""") -> PNG
        url.contains(""".${JPEG.extension}""") -> JPEG
        url.contains(""".${WEBP.extension}""") -> WEBP
        url.contains(""".${AVIF.extension}""") -> AVIF
        url.contains(""".${SVG.extension}""") -> SVG
        else -> return null
    }

    val imageTypePattern =
        PNG.extension +
                "|${JPEG.extension}" +
                "|${WEBP.extension}" +
                "|${AVIF.extension}" +
                "|${SVG.extension}"

    val index = (dir.list() ?: emptyArray()).count {
        Regex("""service_($imageTypePattern)_saved_picture_(\d+)\.($imageTypePattern)""").matches(it)
    }

    val filename = "service_${imageType.extension}_saved_picture_${index}.${imageType.extension}"

    service.openFileOutput(filename, Service.MODE_PRIVATE).use { outputStream ->
        try {
            val inputStream = URL(url).openStream()
            FileUtils.copy(inputStream, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    return File(service.filesDir, filename).absolutePath
}

enum class SupportedImageType(val extension: String) {
    PNG("png"),
    JPEG("jpg"),
    WEBP("webp"),
    AVIF("avif"),
    SVG("svg"),
}

const val keyURLExtra = "URL"

const val imageJPG =
    "https://external-preview.redd.it/VRD72dRPK6a0nyFzudYZeMBoN1_HjvW5VO24JFxnPsk.jpg?auto=webp&s=d319800a24adbed3652ba5ce7cafe947b3a98b2d"
const val imagePNG =
    "https://pics.me.me/genes-of-a-normal-human-genes-of-a-android-dev-47274893.png"
const val imageSVG = "https://cdn.worldvectorlogo.com/logos/android-4.svg"
const val imageWEBP = "https://www.gstatic.com/webp/gallery/4.sm.webp"
const val imageAVIF = "https://jakearchibald.com/c/f1-good-a14c8cc5.avif"
const val imageSVG2 = "https://upload.wikimedia.org/wikipedia/commons/f/fa/Apple_logo_black.svg"