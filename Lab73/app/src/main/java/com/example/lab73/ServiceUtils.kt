package com.example.lab73

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference


class DownloadHandler(service: DownloadImageBoundService) : Handler(service.mainLooper) {
    private val serviceReference = WeakReference(service)

    override fun handleMessage(message: Message) {
        if (message.what == MessageWhat.LOAD_IMAGE.what) {
//            Log.i("lab73log", "Download message incoming")

            val url = message.obj as? String ?: return
            val replyTo = message.replyTo ?: return

            serviceReference.get()?.saveImage(url) {
//                Log.i("lab73log", "Sending response: $it")
                replyTo.send(Message.obtain().apply {
                    obj = it
                    what = MessageWhat.IMAGE_LOCATION.what
                })
            }
        } else super.handleMessage(message)
    }
}

class ResponseHandler(activity: MainActivity) : Handler(activity.mainLooper) {
    private val activityReference = WeakReference(activity)

    override fun handleMessage(message: Message) {
        if (message.what == MessageWhat.IMAGE_LOCATION.what) {
//            Log.d("lab73log", "Save message incoming")

            val location = message.obj as? String ?: return

            activityReference.get()?.showPath(location) ?: return
        } else super.handleMessage(message)
    }
}

enum class SupportedImageType(val extension: String) {
    PNG("png"),
    JPEG("jpg"),
    WEBP("webp"),
    AVIF("avif"),
    SVG("svg"),
}

enum class MessageWhat(val what: Int) {
    LOAD_IMAGE(0),
    IMAGE_LOCATION(1)
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