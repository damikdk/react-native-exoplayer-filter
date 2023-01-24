package com.daasuu.epf;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.MediaController;

import com.daasuu.epf.chooser.EConfigChooser;
import com.daasuu.epf.contextfactory.EContextFactory;
import com.daasuu.epf.filter.AlphaFrameFilter;
import com.daasuu.epf.filter.GlFilter;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.ArrayList;

/**
 * Created by sudamasayuki on 2017/05/16.
 */
public class EPlayerView extends GLSurfaceView implements VideoListener {

    private final static String TAG = EPlayerView.class.getSimpleName();

    private static final ArrayList<EPlayerView> allInstances = new ArrayList<>();

    private final EPlayerRenderer renderer;
    private SimpleExoPlayer player;

    private float videoAspect = 1f;
    private PlayerScaleType playerScaleType = PlayerScaleType.RESIZE_FIT_WIDTH;

    public EPlayerView(Context context) {
        this(context, null);
    }

    public EPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        renderer = new EPlayerRenderer(this);
        allInstances.add(this);
    }

    public EPlayerView setSimpleExoPlayer(SimpleExoPlayer player) {
        if (this.player != null) {
            this.player.release();
            this.player = null;
        }

        setEGLContextFactory(new EContextFactory());
        setEGLConfigChooser(new EConfigChooser());

        setZOrderOnTop(true);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);

        setRenderer(renderer);

        this.player = player;
        this.player.addVideoListener(this);

        this.player.addListener(new Player.EventListener() {
            @Override
            public void onPlaybackStateChanged(@Player.State int state) {
                if (state == Player.STATE_ENDED) {
                    Log.d(TAG, "Video is ended, reset all players");
                    resetAllPlayers();
                }
            }
        });

        this.renderer.setSimpleExoPlayer(player);

        player.setSeekParameters(SeekParameters.CLOSEST_SYNC);

        return this;
    }

    private GlFilter filter = null;

    public void setGlFilter(GlFilter glFilter) {
        renderer.setGlFilter(glFilter);
        if (glFilter != null) {
            if (glFilter instanceof AlphaFrameFilter) {
                videoAspect = videoAspect * 2;
                requestLayout();
            } else {
                if (filter != null) {
                    if (filter instanceof AlphaFrameFilter) {
                        videoAspect = videoAspect / 2;
                        requestLayout();
                    }
                }
            }
        }
        filter = glFilter;
    }

    public static void resetAllPlayers(EPlayerView exceptionView) {
        allInstances.forEach(playerView -> {
            if (exceptionView == playerView) {
                Log.e(TAG, String.valueOf(playerView));
                return;
            }

            playerView.player.seekTo(0);
        });
    }

    public static void resetAllPlayers() {
        allInstances.forEach(playerView -> {
            playerView.player.seekTo(0);
        });
    }

    public void setPlayerScaleType(PlayerScaleType playerScaleType) {
        this.playerScaleType = playerScaleType;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int viewWidth = measuredWidth;
        int viewHeight = measuredHeight;

        switch (playerScaleType) {
            case RESIZE_FIT_WIDTH:
                viewHeight = (int) (measuredWidth / videoAspect);
                break;
            case RESIZE_FIT_HEIGHT:
                viewWidth = (int) (measuredHeight * videoAspect);
                break;
        }

        // Log.d(TAG, "onMeasure viewWidth = " + viewWidth + " viewHeight = " + viewHeight);

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    public void onPause() {
        super.onPause();
        renderer.release();
    }

    //////////////////////////////////////////////////////////////////////////
    // SimpleExoPlayer.VideoListener

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        // Log.d(TAG, "width = " + width + " height = " + height + " unappliedRotationDegrees = " + unappliedRotationDegrees + " pixelWidthHeightRatio = " + pixelWidthHeightRatio);
        videoAspect = ((float) width / height) * pixelWidthHeightRatio;
        // Log.d(TAG, "videoAspect = " + videoAspect);
        requestLayout();
    }

    @Override
    public void onRenderedFirstFrame() {
    }
}
