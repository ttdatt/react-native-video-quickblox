package com.sts.RNQuickblox;

import android.view.ViewGroup;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

import org.webrtc.RendererCommon;
import org.webrtc.VideoRenderer;

/**
 * Created by Dat Tran on 3/28/17.
 */

public class QuickbloxVideoViewManager extends ViewGroupManager<ViewGroup> {

    public static final String VIEW_NAME = "QuickbloxVideoViewManager";

    public QBRTCSurfaceView getVideoView() {
        return videoView;
    }

    protected QBRTCSurfaceView videoView;
    QuickbloxVideoViewGroup viewGroup;

    private ReactContext reactContext;

    @Override
    public String getName() {
        return VIEW_NAME;
    }

    protected QBRTCVideoTrack getVideoTrack() {
        return null;
    }

    @Override
    protected ViewGroup createViewInstance(ThemedReactContext reactContext) {
        this.reactContext = reactContext;

        viewGroup = new QuickbloxVideoViewGroup(reactContext);
        videoView = new QBRTCSurfaceView(reactContext);

//        videoView.setZOrderMediaOverlay(true);
        videoView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);

        QBRTCVideoTrack videoTrack = getVideoTrack();
        if (videoTrack != null) {
            this.renderVideoTrack(videoTrack);
        }
        viewGroup.addView(videoView);

        return viewGroup;
    }

    void renderVideoTrack(QBRTCVideoTrack videoTrack) {
        if (videoView != null && videoTrack != null) {
            videoTrack.removeRenderer(videoTrack.getRenderer());
            videoTrack.addRenderer(new VideoRenderer(videoView));

            if (viewGroup.getId() > 0)
                viewGroup.onRenderedVideoTrack();
        }
    }

    void release() {
        if (this.videoView != null)
            this.videoView.release();
    }


}