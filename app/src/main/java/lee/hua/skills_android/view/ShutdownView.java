package lee.hua.skills_android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

public class ShutdownView extends View implements ViewTreeObserver.OnGlobalLayoutListener {
    private RectF bgRect;
    private Paint bgPaint;
    private RectF showRect;
    private Paint showPaint;
    private int leeHeight = 100;

    public ShutdownView(Context context) {
        super(context);
        init();
    }

    public ShutdownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShutdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bgRect = new RectF(100, 100, 300, 400);
        showRect = new RectF(100, leeHeight, 300, 400);
        bgPaint = new Paint();
        showPaint = new Paint();
        bgPaint.setAntiAlias(true);
        showPaint.setAntiAlias(true);

        bgPaint.setColor(Color.DKGRAY);
        showPaint.setColor(Color.LTGRAY);
    }

    public void startAnim() {
        ValueAnimator va = ValueAnimator.ofInt(100, 400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                showRect = new RectF(100, val, 300, 400);
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(bgRect, 50, 50, bgPaint);
        canvas.drawRoundRect(showRect, 50, 50, showPaint);
    }

    @Override
    public void onGlobalLayout() {
        startAnim();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册布局完成监听器
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除布局完成监听器
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
