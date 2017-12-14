package com.sts.RNQuickblox;

import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

/**
 * Created by Dat Tran on 3/23/17.
 */

public class QuickbloxRemoteVideoViewManager extends QuickbloxVideoViewManager {

    public static final String VIEW_NAME = "QuickbloxRemoteVideoView";


    @Override
    public String getName() {
        return VIEW_NAME;
    }

    public QuickbloxRemoteVideoViewManager() {
        QuickbloxHandler.getInstance().setRemoteVideoViewManager(this);
    }

    @Override
    protected QBRTCVideoTrack getVideoTrack() {
        if (QuickbloxHandler.getInstance().getCaller() != null) {
            QBRTCVideoTrack videoTrack = QuickbloxHandler.getInstance().getSession().getMediaStreamManager().getVideoTrack(QuickbloxHandler.getInstance().getCaller());
            this.renderVideoTrack(videoTrack);
        }
        return null;
    }
}