package com.sts.RNQuickblox;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBSignaling;
import com.quickblox.chat.QBWebRTCSignaling;
import com.quickblox.chat.listeners.QBVideoChatSignalingManagerListener;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCConfig;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionConnectionCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionStateCallback;
import com.quickblox.videochat.webrtc.exception.QBRTCException;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sts on 3/22/17.
 */

public class QuickbloxHandler implements QBRTCSessionStateCallback, QBRTCClientVideoTracksCallbacks, QBRTCSessionConnectionCallbacks {

    private static final String TAG = QuickbloxHandler.class.getSimpleName();

    static final String APP_ID = "53525";
    static final String AUTH_KEY = "PLmye3xQsJ3-Gsr";
    static final String AUTH_SECRET = "TUDPphBZK7r5dVY";
    static final String ACCOUNT_KEY = "6XDmKdXBfwPuJsWv9Fxp";

    private static QuickbloxHandler instance;
    private ReactApplicationContext reactApplicationContext;

    public Integer getCaller() {
        return caller;
    }

    private Integer caller;

    public QuickbloxLocalVideoViewManager getLocalViewManager() {
        return localViewManager;
    }

    public void setLocalViewManager(QuickbloxLocalVideoViewManager localViewManager) {
        this.localViewManager = localViewManager;
    }

    private QuickbloxLocalVideoViewManager localViewManager;

    public QuickbloxRemoteVideoViewManager getRemoteVideoViewManager() {
        return remoteVideoViewManager;
    }

    public void setRemoteVideoViewManager(QuickbloxRemoteVideoViewManager remoteVideoViewManager) {
        this.remoteVideoViewManager = remoteVideoViewManager;
    }

    private QuickbloxRemoteVideoViewManager remoteVideoViewManager;


    public QuickbloxClient getQuickbloxClient() {
        return quickbloxClient;
    }

    public void setQuickbloxClient(QuickbloxClient quickbloxClient, ReactApplicationContext rctCtx) {
        this.quickbloxClient = quickbloxClient;
        reactApplicationContext = rctCtx;
        this.setupQuickblox();
    }

    private QuickbloxClient quickbloxClient;


    public QBRTCSession getSession() {
        return session;
    }

    public void setSession(QBRTCSession session) {
        if (session != null) {
            this.session = session;
            this.session.addSessionCallbacksListener(this);
            this.session.addVideoTrackCallbacksListener(this);
        } else {
            this.session.removeVideoTrackCallbacksListener(this);
            this.session.removeSessionCallbacksListener(this);
            this.session = session;
        }
    }

    private QBRTCSession session;

    public QBUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(QBUser currentUser) {
        this.currentUser = currentUser;
    }

    private QBUser currentUser;

    public QuickbloxHandler() {
    }

    private void setupQuickblox() {
        //        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().init(reactApplicationContext.getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

        QBChatService.setDebugEnabled(true);
        QBRTCConfig.setDebugEnabled(true);
    }

    public void init() {


//        QBVideoChatWebRTCSignalingManager qbVideoChatWebRTCSignalingManager = QBChatService.getInstance().getVideoChatWebRTCSignalingManager();
        QBChatService.getInstance().getVideoChatWebRTCSignalingManager()
                .addSignalingManagerListener(new QBVideoChatSignalingManagerListener() {
                    @Override
                    public void signalingCreated(QBSignaling qbSignaling, boolean createdLocally) {
                        if (!createdLocally) {
                            QBRTCClient.getInstance(reactApplicationContext.getApplicationContext()).addSignaling((QBWebRTCSignaling) qbSignaling);
                        }
                    }
                });

        QBRTCClient.getInstance(reactApplicationContext.getApplicationContext()).addSessionCallbacksListener(new QBRTCClientSessionCallbacks() {
            @Override
            public void onReceiveNewSession(QBRTCSession qbrtcSession) {

                if (session != null && qbrtcSession.getSessionID().equals(session.getSessionID())) {
                    session.rejectCall(new HashMap<String, String>() {{
                        put("key", "value");
                    }});
                    return;
                }

                setSession(qbrtcSession);
                //[self.localViewManager attachLocalCameraStream:self.session];
                quickbloxClient.receiveCallSession(session, Integer.valueOf(session.getUserInfo().get("userId")));
            }

            @Override
            public void onUserNoActions(QBRTCSession qbrtcSession, Integer integer) {

            }

            @Override
            public void onSessionStartClose(QBRTCSession qbrtcSession) {

            }

            @Override
            public void onUserNotAnswer(QBRTCSession qbrtcSession, Integer integer) {

            }

            @Override
            public void onCallRejectByUser(QBRTCSession qbrtcSession, Integer integer, Map<String, String> map) {
                quickbloxClient.userRejectCall(integer);
            }

            @Override
            public void onCallAcceptByUser(QBRTCSession qbrtcSession, Integer integer, Map<String, String> map) {
                quickbloxClient.userAcceptCall(integer);
            }

            @Override
            public void onReceiveHangUpFromUser(QBRTCSession qbrtcSession, Integer integer, Map<String, String> map) {
                QuickbloxHandler.this.release();
                quickbloxClient.userHungUp(integer);
            }

            @Override
            public void onSessionClosed(QBRTCSession qbrtcSession) {
                quickbloxClient.sessionDidClose(qbrtcSession);
                session = null;
            }
        });

        QBRTCClient.getInstance(reactApplicationContext.getApplicationContext()).prepareToProcessCalls();
    }

    public static QuickbloxHandler getInstance() {
        if (instance == null)
            instance = new QuickbloxHandler();

        return instance;
    }

    public void release() {
        if (QuickbloxHandler.this.localViewManager != null)
            QuickbloxHandler.this.localViewManager.release();
        if (QuickbloxHandler.this.remoteVideoViewManager != null)
            QuickbloxHandler.this.remoteVideoViewManager.release();
    }

    public void startCall(List<Integer> userIDs, Integer callRequestId, String realName, String avatar) {
        Log.d(TAG, "start call user: " + userIDs.toString() + " " + realName);

        QBRTCSession session = QBRTCClient.getInstance(reactApplicationContext.getApplicationContext()).createNewSessionWithOpponents(userIDs, QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO);
        this.setSession(session);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("callRequestId", callRequestId.toString());
        userInfo.put("sessionId", session.getSessionID());
        userInfo.put("realName", realName);
        userInfo.put("avatar", avatar);
        session.startCall(userInfo);
    }

    //<editor-fold desc="QBRTCSessionStateCallback">
    @Override
    public void onConnectedToUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onDisconnectedFromUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onConnectionClosedForUser(QBRTCSession qbrtcSession, Integer integer) {

    }
    //</editor-fold>

    //<editor-fold desc="QBRTCClientVideoTracksCallbacks">
    @Override
    public void onLocalVideoTrackReceive(QBRTCSession qbrtcSession, QBRTCVideoTrack qbrtcVideoTrack) {
        Log.d(TAG, "onLocalVideoTrackReceive");
    }

    @Override
    public void onRemoteVideoTrackReceive(QBRTCSession qbrtcSession, QBRTCVideoTrack qbrtcVideoTrack, Integer integer) {
        Log.d(TAG, "onRemoteVideoTrackReceive");
        if (remoteVideoViewManager != null)
            remoteVideoViewManager.renderVideoTrack(qbrtcVideoTrack);
        caller = integer;
    }
    //</editor-fold>

    //<editor-fold desc="QBRTCSessionConnectionCallbacks">
    @Override
    public void onStartConnectToUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onDisconnectedTimeoutFromUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onConnectionFailedWithUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onError(QBRTCSession qbrtcSession, QBRTCException e) {

    }
    //</editor-fold>
}
