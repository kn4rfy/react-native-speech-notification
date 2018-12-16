//
//  RNSpeechNotificationModule.java
//  RNSpeechNotificationModule
//
//  Created by Francis Knarfy Elopre on 05/08/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

package com.kn4rfy.rn.speechnotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.support.v4.app.NotificationCompat

import com.facebook.common.logging.FLog
import com.facebook.react.bridge.GuardedAsyncTask
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.ReactConstants

import org.json.JSONException

import java.util.Locale

class RNSpeechNotificationModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {

    init {
        reactApplicationContext.addLifecycleEventListener(this)
        initialize()
    }

    override fun getName(): String {
        return "SpeechNotification"
    }

    override fun initialize() {
        textToSpeech = TextToSpeech(reactApplicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.SUCCESS) {
                FLog.e(ReactConstants.TAG, "Text-to-Speech not initialized")
                isTextToSpeechInitialized = false
            } else {
                FLog.v(ReactConstants.TAG, "Text-to-Speech initialized")
                isTextToSpeechInitialized = true
            }
        })
    }

    override fun onHostResume() {
        isVisible = true
    }

    override fun onHostPause() {
        isVisible = false
    }

    override fun onHostDestroy() {
        // Activity `onDestroy`
    }

    internal fun getResIdForDrawable(clsName: String, resPath: String): Int {
        val drawable = this.getBaseName(resPath)
        var resId = 0

        try {
            val cls = Class.forName("$clsName.R\$drawable")

            resId = cls.getDeclaredField(drawable).get(Int::class.java) as Int
        } catch (ignore: Exception) {
        }

        return resId
    }

    private fun getBaseName(resPath: String): String {
        var drawable = resPath

        if (drawable.contains("/")) {
            drawable = drawable.substring(drawable.lastIndexOf('/') + 1)
        }

        if (resPath.contains(".")) {
            drawable = drawable.substring(0, drawable.lastIndexOf('.'))
        }

        return drawable
    }

    @ReactMethod
    @Throws(JSONException::class, NullPointerException::class)
    fun speak(args: ReadableMap?) {
        object : GuardedAsyncTask<Void, Void>(reactApplicationContext) {
            override fun doInBackgroundGuarded(vararg params: Void) {
                val title: String
                val message: String
                val language: String

                if (!isTextToSpeechInitialized) {
                    FLog.e(ReactConstants.TAG, "Text-to-Speech is not initialized")
                    return
                }

                if (args == null) {
                    FLog.e(ReactConstants.TAG, "Speak parameters must not be null")
                    return
                }

                if (args.isNull("message")) {
                    FLog.e(ReactConstants.TAG, "Parameter 'message' must not be null")
                    return
                } else {
                    message = args.getString("message")
                }

                if (args.isNull("language")) {
                    language = "en-US"
                } else if (args.getString("language") == "en") {
                    language = "en-US"
                } else {
                    language = args.getString("language")
                }

                val languageArgs = language.split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                textToSpeech!!.language = Locale(languageArgs[0], languageArgs[1])

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
                } else {
                    textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null)
                }
            }
        }.execute()
    }

    @ReactMethod
    @Throws(JSONException::class, NullPointerException::class)
    fun notify(args: ReadableMap?) {
        object : GuardedAsyncTask<Void, Void>(reactApplicationContext) {
            override fun doInBackgroundGuarded(vararg params: Void) {
                val title: String
                val message: String
                val language: String

                if (!isTextToSpeechInitialized) {
                    FLog.e(ReactConstants.TAG, "Text-to-Speech is not initialized")
                    return
                }

                if (args == null) {
                    FLog.e(ReactConstants.TAG, "Speak parameters must not be null")
                    return
                }

                if (args.isNull("title")) {
                    FLog.e(ReactConstants.TAG, "Parameter 'title' must not be null")
                    return
                } else {
                    title = args.getString("title")
                }

                if (args.isNull("message")) {
                    FLog.e(ReactConstants.TAG, "Parameter 'message' must not be null")
                    return
                } else {
                    message = args.getString("message")
                }

                if (args.isNull("language")) {
                    language = "en-US"
                } else if (args.getString("language") == "en") {
                    language = "en-US"
                } else {
                    language = args.getString("language")
                }

                val emptyIntent = Intent()
                val context = reactApplicationContext
                val pendingIntent = PendingIntent.getActivity(context, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                val notificationBuilder = NotificationCompat.Builder(context)
                        .setSmallIcon(getResIdForDrawable(context.packageName, args.getString("icon")))
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val languageArgs = language.split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                textToSpeech!!.language = Locale(languageArgs[0], languageArgs[1])

                // if (isVisible) {
                //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //     textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
                //   } else {
                //     textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
                //   }
                // } else {
                vibrator.vibrate(250)
                notificationManager.notify(0, notificationBuilder.build())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
                } else {
                    textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null)
                }
                // }
            }
        }.execute()
    }

    companion object {
        private var isTextToSpeechInitialized = false
        private var isVisible = false
        private var textToSpeech: TextToSpeech? = null
    }
}
