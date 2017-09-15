package com.sts.RNQuickblox;

import com.quickblox.videochat.webrtc.QBRTCCameraVideoCapturer;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

/**
 * Created by sts on 3/23/17.
 */

public class QuickbloxLocalVideoViewManager extends QuickbloxVideoViewManager {

    public static final String VIEW_NAME = "QuickbloxLocalVideoView";

    @Override
    public String getName() {
        return VIEW_NAME;
    }

    public QuickbloxLocalVideoViewManager() {
        QuickbloxHandler.getInstance().setLocalViewManager(this);
    }

    @Override
    protected QBRTCVideoTrack getVideoTrack() {
        if (QuickbloxHandler.getInstance().getSession() != null) {
            QBRTCSession session = QuickbloxHandler.getInstance().getSession();
            QBRTCCameraVideoCapturer videoCapturer = (QBRTCCameraVideoCapturer) (session.getMediaStreamManager().getVideoCapturer());
            videoCapturer.changeCaptureFormat(768, 1024, 30);
            return session.getMediaStreamManager().getLocalVideoTrack();
        }
        return null;
    }
}