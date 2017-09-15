
package com.sts.RNQuickblox;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNQuickbloxModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNQuickbloxModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNQuickblox";
  }
}