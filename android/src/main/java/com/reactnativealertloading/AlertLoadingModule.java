package com.reactnativealertloading;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.wang.avi.AVLoadingIndicatorView;

@ReactModule(name = AlertLoadingModule.NAME)
public class AlertLoadingModule extends ReactContextBaseJavaModule {
  public static final String NAME = "AlertLoading";

  @Nullable
  private Dialog presentedDialog;
  private boolean isPresenting = false;

  public AlertLoadingModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  public static String asUpperCaseFirstChar(final String target) {
    if ((target == null) || (target.length() == 0)) {
      return target; // You could omit this check and simply live with an
      // exception if you like
    }
    return Character.toUpperCase(target.charAt(0))
      + (target.length() > 1 ? target.substring(1) : "");
  }

  private AVLoadingIndicatorView createLoadingView(String indicator, int size, ViewGroup parent) {
    AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(parent.getContext());
    avLoadingIndicatorView.setIndicator(indicator);
    parent.addView(avLoadingIndicatorView);
    avLoadingIndicatorView.setLayoutParams(new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
    avLoadingIndicatorView.show();
    return avLoadingIndicatorView;
  }

  private CircularProgressDrawable createCircleProgress(int size, ViewGroup parent) {
    AppCompatImageView mCircleView = new AppCompatImageView(parent.getContext());
    CircularProgressDrawable mProgress = new CircularProgressDrawable(parent.getContext());
    mProgress.setSizeParameters(11, 2, 0, 0);
    mProgress.setColorSchemeColors(Color.RED);
    mProgress.start();
    mCircleView.setImageDrawable(mProgress);
    mCircleView.setLayoutParams(new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
    parent.addView(mCircleView);
    return mProgress;
  }

  @ReactMethod
  public void showLoading(ReadableMap params) {
    if (isPresenting) return;
    isPresenting = true;
    if (presentedDialog != null) return;
    Activity activity = getCurrentActivity();
    if (activity == null) return;

    int size = (int) PixelUtil.toPixelFromDIP(50);

    @Nullable
    CircularProgressDrawable mProgress = null;
    @Nullable
    AVLoadingIndicatorView avLoadingIndicatorView = null;

    FrameLayout frameLayout = new FrameLayout(activity);
    frameLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

    if (!params.hasKey("type")) throw new RuntimeException("Type must be present!");

    String type = params.getString("type");
    if ("circleStrokeSpin".equals(type)) {
      mProgress = createCircleProgress(size, frameLayout);
    } else {
      String indicator = String.format("%sIndicator", asUpperCaseFirstChar(type));
      avLoadingIndicatorView = createLoadingView(indicator, size, frameLayout);
    }

    if (params.hasKey("overlayColor")) {
      frameLayout.setBackgroundColor(ColorPropConverter.getColor(params.getDouble("overlayColor"), activity));
    }

    if (params.hasKey("color")) {
      Integer color = ColorPropConverter.getColor(params.getDouble("color"), activity);
      if (mProgress != null) mProgress.setColorSchemeColors(color);
      if (avLoadingIndicatorView != null) avLoadingIndicatorView.setIndicatorColor(color);
    }

    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(frameLayout);
    final Window window = dialog.getWindow();
    window.setLayout(MATCH_PARENT, MATCH_PARENT);
    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.show();
    presentedDialog = dialog;
    isPresenting = false;
  }

  @ReactMethod
  public void hideLoading(@Nullable ReadableMap params) {
    if (presentedDialog == null) return;
    presentedDialog.dismiss();
    presentedDialog = null;
  }
}
