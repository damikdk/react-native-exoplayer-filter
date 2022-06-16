package com.daasuu.epf

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext

class FilterVideoViewPackage : ReactPackage {
    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return emptyList()
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<FilterVideoViewManager> {
        return listOf(FilterVideoViewManager())
    }
}
