//
//  RCTSpeechNotificationModule.java
//  RCTSpeechNotificationModule
//
//  Created by Francis Knarfy Elopre on 05/08/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

package co.ulimit.speechnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.ReactConstants;

import org.json.JSONException;

import java.util.Locale;

public class RCTSpeechNotificationModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  protected static boolean isTextToSpeechInitialized = false;
  protected static boolean isVisible = false;
  private static TextToSpeech textToSpeech;

  public RCTSpeechNotificationModule(ReactApplicationContext reactContext) {
    super(reactContext);
    getReactApplicationContext().addLifecycleEventListener(this);
    initialize();
  }

  @Override
  public String getName() {
    return "SpeechNotification";
  }

  @Override
  @SuppressWarnings("deprecation")
  public void initialize() {
    textToSpeech = new TextToSpeech(getReactApplicationContext(), new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status != TextToSpeech.SUCCESS) {
          FLog.e(ReactConstants.TAG,"Text-to-Speech not initialized");
          isTextToSpeechInitialized = false;
        } else {
          FLog.v(ReactConstants.TAG,"Text-to-Speech initialized");
          isTextToSpeechInitialized = true;
        }
      }
    });
  }

  @Override
  public void onHostResume() {
    isVisible = true;
  }

  @Override
  public void onHostPause() {
    isVisible = false;
  }

  @Override
  public void onHostDestroy() {
    // Activity `onDestroy`
  }

  final int getResIdForDrawable(String clsName, String resPath) {
    String drawable = this.getBaseName(resPath);
    int resId = 0;

    try {
      Class<?> cls  = Class.forName(clsName + ".R$drawable");

      resId = (Integer) cls.getDeclaredField(drawable).get(Integer.class);
    } catch (Exception ignore) {}

    return resId;
  }

  private String getBaseName (String resPath) {
    String drawable = resPath;

    if (drawable.contains("/")) {
      drawable = drawable.substring(drawable.lastIndexOf('/') + 1);
    }

    if (resPath.contains(".")) {
      drawable = drawable.substring(0, drawable.lastIndexOf('.'));
    }

    return drawable;
  }

  @ReactMethod
  @SuppressWarnings("deprecation")
  public void speak(final ReadableMap args) throws JSONException, NullPointerException {
    new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) {
      @Override
      protected void doInBackgroundGuarded(Void... params) {
        String title;
        String message;
        String language;

        if (!isTextToSpeechInitialized) {
          FLog.e(ReactConstants.TAG, "Text-to-Speech is not initialized");
          return;
        }

        if (args == null) {
          FLog.e(ReactConstants.TAG, "Speak parameters must not be null");
          return;
        }

        if (args.isNull("title")) {
          FLog.e(ReactConstants.TAG, "Parameter 'title' must not be null");
          return;
        } else {
          title = args.getString("title");
        }

        if (args.isNull("message")) {
          FLog.e(ReactConstants.TAG, "Parameter 'message' must not be null");
          return;
        } else {
          message = args.getString("message");
        }

        if (args.isNull("language")) {
          language = "en-US";
        } else if (args.getString("language").equals("en")) {
          language = "en-US";
        } else {
          language = args.getString("language");
        }

        final Intent emptyIntent = new Intent();
        Context context = getReactApplicationContext();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(getResIdForDrawable(context.getPackageName(), args.getString("icon")))
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String[] languageArgs = language.split("-");
        textToSpeech.setLanguage(new Locale(languageArgs[0], languageArgs[1]));

        if (isVisible) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
          } else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
          }
        } else {
          vibrator.vibrate(250);
          notificationManager.notify(0, notificationBuilder.build());
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
          } else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
          }
        }
      }
    }.execute();
  }
}
