package com.sts.RNQuickblox;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.core.result.HttpStatus;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by sts on 3/22/17.
 */

public class RNQuickbloxModule extends ReactContextBaseJavaModule {
    private static final String TAG = RNQuickbloxModule.class.getSimpleName();

    private static final String DID_RECEIVE_CALL_SESSION = "DID_RECEIVE_CALL_SESSION";
    private static final String USER_ACCEPT_CALL = "USER_ACCEPT_CALL";
    private static final String USER_REJECT_CALL = "USER_REJECT_CALL";
    private static final String USER_HUNG_UP = "USER_HUNG_UP";
    private static final String SESSION_DID_CLOSE = "SESSION_DID_CLOSE";

    private ReactApplicationContext reactApplicationContext;
    private Gson gson;

    public RNQuickbloxModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactApplicationContext = reactContext;
        QuickbloxHandler.getInstance().setQuickbloxClient(this, reactContext);
        this.gson = new Gson();
    }

    private JavaScriptModule getJSModule() {
        return reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    @Override
    public String getName() {
        return "RNQuickblox";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DID_RECEIVE_CALL_SESSION, DID_RECEIVE_CALL_SESSION);
        constants.put(USER_ACCEPT_CALL, USER_ACCEPT_CALL);
        constants.put(USER_REJECT_CALL, USER_REJECT_CALL);
        constants.put(USER_HUNG_UP, USER_HUNG_UP);
        constants.put(SESSION_DID_CLOSE, SESSION_DID_CLOSE);
        return constants;
    }

    @ReactMethod
    public void connectUser(String userId, String password, Callback callback) {
        this.login(userId, password, callback);
    }

    @ReactMethod
    public void signUp(final String userName, final String password, String realName, String email, final Callback callback) {
        final QBUser user = new QBUser();
        user.setLogin(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullName(realName);
        QBUsers.signUp(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                QuickbloxHandler.getInstance().setCurrentUser(qbUser);
                login(userName, password, callback);
            }

            @Override
            public void onError(QBResponseException e) {
                if (e.getHttpStatusCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                    login(userName, password, callback);
                } else {
                    callback.invoke(e.getMessage());
                }
            }
        });
    }

    @ReactMethod
    public void getUsers(final Callback callback) {
        QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
        pagedRequestBuilder.setPage(1);
        pagedRequestBuilder.setPerPage(50);

        QBUsers.getUsers(pagedRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                Log.i(TAG, "Users: " + qbUsers.toString());
                callback.invoke(gson.toJson(qbUsers));
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    @ReactMethod
    public void callToUsers(ReadableArray userIDs, final Integer callRequestId, final String realName, final String avatar) {
        List<Integer> ids = new ArrayList<>();
//        ids.add(25581924);

        for (int i = 0; i < userIDs.size(); i++)
            ids.add(userIDs.getInt(i));

        QuickbloxHandler.getInstance().startCall(ids, callRequestId, realName, avatar);
    }

    private void login(String userId, String password, final Callback callback) {
        final QBUser user = new QBUser(userId, password);
        QBAuth.createSession(user).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                user.setId(qbSession.getUserId());
                QBChatService chatService = QBChatService.getInstance();
                chatService.login(user, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        QuickbloxHandler.getInstance().setCurrentUser(user);
                        QuickbloxHandler.getInstance().init();
                        callback.invoke(user.getId());
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        callback.invoke(e.getMessage());
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    @ReactMethod
    public void acceptCall() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("key", "value");
        QuickbloxHandler.getInstance().getSession().acceptCall(userInfo);
    }

    @ReactMethod
    public void hangup() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("key", "value");
        QuickbloxHandler.getInstance().getSession().hangUp(userInfo);
        QuickbloxHandler.getInstance().setSession(null);

        QuickbloxHandler.getInstance().release();
    }

    @ReactMethod
    public void rejectCall() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("key", "value");
        QuickbloxHandler.getInstance().getSession().rejectCall(userInfo);
        QuickbloxHandler.getInstance().setSession(null);
    }

    public void receiveCallSession(QBRTCSession session, Integer userId) {
        WritableMap params = Arguments.createMap();
        params.putInt("userId", userId);
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(DID_RECEIVE_CALL_SESSION, params);
    }

    public void userAcceptCall(Integer userId) {
        WritableMap params = Arguments.createMap();
        params.putString("", "");
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(USER_ACCEPT_CALL, params);
    }

    public void userRejectCall(Integer userId) {
        WritableMap params = Arguments.createMap();
        params.putString("", "");
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(USER_REJECT_CALL, params);
    }

    public void userHungUp(Integer userId) {
        WritableMap params = Arguments.createMap();
        params.putString("", "");
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(USER_HUNG_UP, params);
    }

    public void sessionDidClose(QBRTCSession session) {
        WritableMap params = Arguments.createMap();
        params.putString("", "");
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(SESSION_DID_CLOSE, params);
    }
}
