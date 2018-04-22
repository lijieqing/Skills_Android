package lee.hua.skills_android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * 仿时钟动画
 * @author lijie
 */
public class ClockView extends View {
    private Paint mPaint = null;
    private int mWidth, mHeight;
    private float radius;
    private static final int TIME = 12;
    private float mHour, mMinute, mSecond;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        radius = mWidth * 0.5f / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //移动坐标
        canvas.translate(mWidth / 2, mHeight / 2);

        drawDial(canvas);

        drawClockHand(canvas);

        invalidate();
    }

    /**
     * 绘制表盘
     */
    private void drawDial(Canvas canvas){
        canvas.save();
        //绘制圆盘
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        canvas.drawCircle(0, 0, radius, mPaint);

        for (int i = 0; i < TIME; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                mPaint.setStrokeWidth(10);
            } else {
                mPaint.setStrokeWidth(5);
            }
            canvas.drawLine(0, -radius, 0, -radius + 80, mPaint);

            mPaint.setTextSize(30);
            canvas.rotate(360 / TIME);
        }
        canvas.restore();
    }
    /**
     * 绘制表针
     */
    private void drawClockHand(Canvas canvas) {
        mPaint.setStrokeWidth(6);
        //获取时间
        Calendar calendar = Calendar.getInstance();
        float milliSecond = calendar.get(Calendar.MILLISECOND);
        mSecond = calendar.get(Calendar.SECOND) + milliSecond / 1000;
        mMinute = calendar.get(Calendar.MINUTE) + mSecond / 60;
        mHour = calendar.get(Calendar.HOUR) + mMinute / 60;

        float secondDegree = mSecond/60*360;
        float minutedDegree = mMinute/60*360;
        float hourDegree = mHour/12*360;
        //绘制秒针
        canvas.save();
        canvas.rotate(secondDegree);
        mPaint.setColor(Color.WHITE);
        canvas.drawLine(0,0,0,-radius+radius/4,mPaint);
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(minutedDegree);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0,0,0,-radius+radius/3,mPaint);
        canvas.restore();
        //绘制时针
        canvas.save();
        canvas.rotate(hourDegree);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0,0,0,-radius+radius/2,mPaint);
        canvas.restore();
    }
}
