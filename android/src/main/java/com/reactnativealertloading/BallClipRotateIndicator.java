package com.reactnativealertloading;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import android.animation.ValueAnimator;

import com.facebook.react.uimanager.PixelUtil;
import com.wang.avi.Indicator;

import java.util.ArrayList;

public class BallClipRotateIndicator extends Indicator {

  float scaleFloat = 1, degrees;

  @Override
  public void draw(Canvas canvas, Paint paint) {
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(PixelUtil.toPixelFromDIP(3));

    float circleSpacing = PixelUtil.toPixelFromDIP(8);
    float x = (getWidth()) / 2;
    float y = (getHeight()) / 2;
    canvas.translate(x, y);
    canvas.scale(scaleFloat, scaleFloat);
    canvas.rotate(degrees);
    RectF rectF = new RectF(-x + circleSpacing, -y + circleSpacing, 0 + x - circleSpacing, 0 + y - circleSpacing);
    canvas.drawArc(rectF, -45, 270, false, paint);
  }

  @Override
  public ArrayList<ValueAnimator> onCreateAnimators() {
    ArrayList<ValueAnimator> animators = new ArrayList<>();
    ValueAnimator rotateAnim = ValueAnimator.ofFloat(0, 180, 360);
    rotateAnim.setDuration(750);
    rotateAnim.setRepeatCount(-1);
    addUpdateListener(rotateAnim, new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        degrees = (float) animation.getAnimatedValue();
        postInvalidate();
      }
    });
    //animators.add(scaleAnim);
    animators.add(rotateAnim);
    return animators;
  }

}
