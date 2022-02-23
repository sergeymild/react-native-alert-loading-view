package com.reactnativealertloading;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

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
  private boolean isFading = false;

  public AlertLoadingModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  @ReactMethod
  public void showLoading(ReadableMap params) {
    if (isPresenting) return;
    isPresenting = true;
    if (presentedDialog != null) return;
    System.out.println("showLoading");

    int size = (int) PixelUtil.toPixelFromDIP(50);

    @Nullable
    CircularProgressDrawable mProgress = null;
    @Nullable
    AVLoadingIndicatorView avLoadingIndicatorView = null;

    FrameLayout frameLayout = new FrameLayout(getCurrentActivity());
    frameLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

    if (params.hasKey("type")) {
      switch (params.getString("type")) {
        case "circleStrokeSpin":
          AppCompatImageView mCircleView = new AppCompatImageView(getCurrentActivity());
          mProgress = new CircularProgressDrawable(getCurrentActivity());
          mProgress.setSizeParameters(11, 2, 0, 0);
          mProgress.setColorSchemeColors(Color.RED);
          mProgress.start();
          mCircleView.setImageDrawable(mProgress);
          mCircleView.setLayoutParams(new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
          frameLayout.addView(mCircleView);
          break;
        case "ballClipRotate":
          avLoadingIndicatorView = new AVLoadingIndicatorView(getCurrentActivity());
          avLoadingIndicatorView.setIndicator("BallClipRotateIndicator");
          frameLayout.addView(avLoadingIndicatorView);
          avLoadingIndicatorView.setLayoutParams(new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
          avLoadingIndicatorView.show();
          break;
        case "ballScaleRippleMultiple":
          avLoadingIndicatorView = new AVLoadingIndicatorView(getCurrentActivity());
          avLoadingIndicatorView.setIndicator("BallScaleRippleMultipleIndicator");
          frameLayout.addView(avLoadingIndicatorView);
          avLoadingIndicatorView.setLayoutParams(new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
          avLoadingIndicatorView.show();
          break;
        case "ballSpinFadeLoader":
          avLoadingIndicatorView = new AVLoadingIndicatorView(getCurrentActivity());
          avLoadingIndicatorView.setIndicator("BallSpinFadeLoaderIndicator");
          frameLayout.addView(avLoadingIndicatorView);
          avLoadingIndicatorView.setLayoutParams(new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
          avLoadingIndicatorView.show();
          break;
      }
    }

    if (params.hasKey("overlayColor")) {
      frameLayout.setBackgroundColor(ColorPropConverter.getColor(params.getDouble("overlayColor"), getCurrentActivity()));
    }

    if (params.hasKey("color")) {
      Integer color = ColorPropConverter.getColor(params.getDouble("color"), getCurrentActivity());
      if (mProgress != null) mProgress.setColorSchemeColors(color);
      if (avLoadingIndicatorView != null) avLoadingIndicatorView.setIndicatorColor(color);
    }

    final Dialog dialog = new Dialog(getCurrentActivity());
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
    if (isFading) return;
    isFading = true;
    if (presentedDialog == null) return;
    presentedDialog.dismiss();
    presentedDialog = null;
    isFading = false;
    System.out.println("hideLoading");
  }
}
