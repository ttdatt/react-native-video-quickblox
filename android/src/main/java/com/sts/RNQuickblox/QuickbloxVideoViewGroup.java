package com.sts.RNQuickblox;

import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by Dat Tran on 4/19/17.
 */

public class QuickbloxVideoViewGroup extends RelativeLayout {

    ReactContext reactContext;

    public QuickbloxVideoViewGroup(ReactContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onRenderedVideoTrack();
    }

    void onRenderedVideoTrack() {
        WritableMap event = Arguments.createMap();
        event.putString("message", "MyMessage");

        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "topChange",
                event);
    }
}
