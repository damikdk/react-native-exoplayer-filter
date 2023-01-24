package com.daasuu.epf

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.daasuu.epf.filter.AlphaFrameFilter
import com.daasuu.epf.filter.GlSepiaFilter
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import com.rnexoplayerfilter.R
import kotlin.math.roundToInt


class FilterVideoViewManager : SimpleViewManager<EPlayerView>() {
    override fun getName(): String {
        return REACT_CLASS
    }

    override fun createViewInstance(reactContext: ThemedReactContext): EPlayerView {
        return EPlayerView(reactContext)
    }

    @ReactProp(name = "source")
    fun setSrc(ePlayerView: EPlayerView, src: ReadableMap) {

        val context: Context = ePlayerView.context.applicationContext
        val urlPath = src.getString("uri")

        Log.d(REACT_CLASS, "New video playing URL: $urlPath")

        if (TextUtils.isEmpty(urlPath)) {
            Log.e(REACT_CLASS, "URL is not valid")
            return
        }

        var uri = Uri.parse(urlPath)

        if (urlPath == "origin") {
//            val originStream: InputStream = context.resources.openRawResource(com.rnexoplayerfilter.R.raw.girlorig)
            uri = RawResourceDataSource.buildRawResourceUri(R.raw.girl_android)

        } else if (urlPath == "mask") {
            uri = RawResourceDataSource.buildRawResourceUri(R.raw.mask)
        }

        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, java.lang.String.valueOf(R.string.app_name))
        )

        // This is the MediaSource representing the media to be played.
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)

        // SimpleExoPlayer
        val player = ExoPlayerFactory.newSimpleInstance(context)
        player.prepare(videoSource)
//        player.setMediaItem(MediaItem.fromUri(uri))
        player.playWhenReady = true
//        player.repeatMode = Player.REPEAT_MODE_ALL

        ePlayerView.setSimpleExoPlayer(player)
        ePlayerView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

//        ePlayerView.setSource(uri)
//        ePlayerView.play()
    }

    @ReactProp(name = "filter")
    fun setFilter(ePlayerView: EPlayerView, filterName: String?) {
        if (TextUtils.isEmpty(filterName)) {
            Log.e(REACT_CLASS, "There is no filter name")
            return
        }

        when (filterName) {
            "alphaMask" -> ePlayerView.setGlFilter(AlphaFrameFilter())
            "sepia" -> ePlayerView.setGlFilter(GlSepiaFilter())
        }
    }

    companion object {
        const val REACT_CLASS = "FilterVideo"
    }
}

enum class FilterType {
    ALPHA_MASK,
    SEPIA
}

