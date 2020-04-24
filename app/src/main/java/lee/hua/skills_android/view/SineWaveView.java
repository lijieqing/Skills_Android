package lee.hua.skills_android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lijie on 2018/3/20.
 * 正弦运动曲线
 */
public class SineWaveView extends View implements ViewTreeObserver.OnGlobalLayoutListener {
    Paint mPaint;
    int width, height;
    float sinY;
    Path sinePath;
    List<SineClass> sineList;
    int circleNum = 40;
    ValueAnimator animator;

    public SineWaveView(Context context) {
        super(context);
        init();
    }

    public SineWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SineWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, height / 2);
        if (sineList == null) {
            canvas.drawCircle(width / 2, 0, width / 3, mPaint);
        } else {
            //canvas.drawColor(Color.WHITE);
            canvas.drawColor(Color.argb(0, 100, 182, 218));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStyle(Paint.Style.FILL);

            for (SineClass sineClass : sineList) {
                sinY = (float) (sineClass.sineA * Math.sin(sineClass.sineW * sineClass.sineX));
                mPaint.setColor(sineClass.color);
                canvas.drawCircle(sineClass.sineX, sinY, sineClass.radius, mPaint);
            }

        }
    }

    /**
     * 初始化基本数据
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //开启硬件加速
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    /**
     * 开始动画
     */
    private void startAnim() {
        if (animator == null) {
            sineList = new ArrayList<>();
            float sinA = height / 4 - 10;
            float sineT = width / 8;
            float sineW = (float) (2 * Math.PI / sineT);

            for (int i = 0; i < circleNum; i++) {
                SineClass sc = new SineClass(sinA, sineT, 10 + i);
                sineList.add(sc);
            }

            sinePath = new Path();
            sinePath.moveTo(0, 0);
            for (int i = 0; i < width; i++) {
                sinePath.lineTo(i, (float) (sinA * Math.sin(sineW * i)));
            }

            System.out.println("sineA ---------- " + (height / 2 - 10));
            System.out.println("sineT ---------- " + width / 4);
            animator = ValueAnimator.ofFloat(0, width);
            animator.setDuration(8 * 1000);
            animator.setRepeatCount(-1);
            animator.setStartDelay(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    for (SineClass sineClass : sineList) {
                        sineClass.updateX();
                    }
                    postInvalidate();
                }
            });
            animator.start();
        }
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
        //关闭硬件加速
        //setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
    }

    /**
     * 正弦参数对象
     */
    class SineClass {
        /**
         * 随机差值
         */
        float randomDIF;
        /**
         * sineA 振幅, sineW = 2π/T, sineT 周期
         */
        float sineA, sineW, sineT;
        /**
         * 当前坐标
         */
        float sineX;
        /**
         * 圆点半径
         */
        float radius;
        /**
         * 随机颜色
         */
        int color;
        private int[] colors = new int[]{
                Color.BLACK,
                Color.DKGRAY,
                Color.GRAY,
                Color.LTGRAY,
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
        };

        SineClass(float sineA, float sineT, float radius) {
            this.sineA = sineA;
            this.sineT = sineT;
            this.sineW = (float) (2 * Math.PI / sineT);
            this.randomDIF = (Math.random() * 10 > 5) ? (float) (Math.random() * width) : -(float) (Math.random() * width);
            this.radius = (float) (Math.random() * 15 + 5f);
            sineX = randomDIF;
            //color = colors[(int) ((colors.length - 1) * Math.random())];
            color = colors[3];
        }

        void updateX() {
            if (sineX < width) {
                sineX = sineX + 3;
            } else {
                sineX = 0;
            }
        }
    }
}
