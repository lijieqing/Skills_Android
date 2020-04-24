package lee.hua.skills_android.view.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by lijie on 2018/3/14.
 */

public class PathSearch extends View {
    Paint mPaint;
    int width, height;
    float rotate;
    final int radius = 200;
    ValueAnimator animator;

    public PathSearch(Context context) {
        this(context, null);
    }

    public PathSearch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathSearch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(2000);
        animator.setStartDelay(500);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotate = (float) animation.getAnimatedValue();
                System.out.println("-------rotate-------" + rotate);
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        canvas.translate(width / 2, height / 2);
        canvas.rotate(90);

        canvas.save();

        Path path = new Path();
        Path dst = new Path();
        path.addCircle(0, 0, radius, Path.Direction.CW);

        PathMeasure pm = new PathMeasure(path, false);
        float endPos = (pm.getLength() / 360) * rotate;
        float start = (float) (endPos - ((0.5 - Math.abs(rotate / 360 - 0.5)) * 300f));
        pm.getSegment(start, endPos, dst, true);

        canvas.drawPath(dst, mPaint);

        canvas.restore();
    }

}
