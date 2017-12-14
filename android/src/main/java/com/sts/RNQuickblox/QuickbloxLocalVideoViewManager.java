package com.sts.RNQuickblox;

import com.quickblox.videochat.webrtc.QBMediaStreamManager;
import com.quickblox.videochat.webrtc.QBRTCCameraVideoCapturer;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

/**
 * Created by Dat Tran on 3/23/17.
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
            QBMediaStreamManager mediaStreamManager = session.getMediaStreamManager();
            if (mediaStreamManager != null) {
                QBRTCCameraVideoCapturer videoCapturer = (QBRTCCameraVideoCapturer) (mediaStreamManager.getVideoCapturer());
                videoCapturer.changeCaptureFormat(768, 1024, 30);
                return mediaStreamManager.getLocalVideoTrack();
            }
        }
        return null;
    }
}