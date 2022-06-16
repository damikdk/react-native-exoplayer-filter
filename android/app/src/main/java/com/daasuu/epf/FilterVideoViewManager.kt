package com.daasuu.epf

import android.net.Uri
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class FilterVideoViewManager : SimpleViewManager<EPlayerView>() {

    override fun getName(): String {
        return REACT_CLASS
    }

    override fun createViewInstance(reactContext: ThemedReactContext): EPlayerView {
        return EPlayerView(reactContext)
    }

    @ReactProp(name = "src")
    fun setSrc(ePlayerView: EPlayerView, urlPath: String?) {
        val uri = Uri.parse(urlPath)

//        ePlayerView.setSource(uri)
//        ePlayerView.play()
    }

    companion object {
        const val REACT_CLASS = "FilterVideo"
    }
}

