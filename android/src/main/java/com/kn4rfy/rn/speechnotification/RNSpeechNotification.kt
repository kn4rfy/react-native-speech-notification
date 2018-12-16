//
//  RNSpeechNotification.java
//  RNSpeechNotification
//
//  Created by Francis Knarfy Elopre on 05/08/2016.
//  Copyright © 2016 Francis Knarfy Elopre. All rights reserved.
//

package com.kn4rfy.rn.speechnotification

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

import java.util.ArrayList
import java.util.Collections.emptyList

class RNSpeechNotification : ReactPackage {

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules = ArrayList<NativeModule>()
        modules.add(RNSpeechNotificationModule(reactContext))
        return modules
    }

    override fun createJSModules(): List<Class<out JavaScriptModule>> {
        return emptyList<Class<out JavaScriptModule>>()
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList<ViewManager<*, *>>()
    }
}
