package lee.hua.skills_android.view.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import lee.hua.skills_android.R;

/**
 * Created by lijie on 2018/3/13.
 */

public class PathCut extends View {
    Paint mPaint;
    int width, height;
    Bitmap plane;
    Matrix matrix;

    float currentPos;
    /**
     * pos[0] 为x坐标
     * pos[1] 为y坐标
     */
    float[] pos;
    /**
     * tan[0] 对应角度的cos值，对应X坐标
     * tan[1] 对应角度的sin值，对应Y坐标
     */
    float[] tan;

    public PathCut(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        plane = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_plane, options);
        matrix = new Matrix();

        pos = new float[2];
        tan = new float[2];
    }

    public PathCut(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathCut(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(width / 2, height / 2);

        currentPos += 0.005;
        if (currentPos >= 1) {
            currentPos = 0;
        }
        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);

        PathMeasure pm = new PathMeasure(path, false);

        pm.getPosTan(pm.getLength() * currentPos, pos, tan);

        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);

        System.out.println("degree------------------------" + degrees);
        System.out.println("pos[0]--------" + pos[0]);
        System.out.println("pos[1]--------" + pos[1]);
        System.out.println("tan[0]--------" + tan[0]);
        System.out.println("tan[1]--------" + tan[1]);
        matrix.reset();
        // 旋转图片
        matrix.postRotate(degrees, plane.getWidth() / 2, plane.getHeight() / 2);
        // 将图片绘制中心调整到与当前点重合
        matrix.postTranslate(pos[0] - plane.getWidth() / 2, pos[1] - plane.getHeight() / 2);

        Path out = new Path();
        out.addCircle(0, 0, 15, Path.Direction.CW);
        out.lineTo(100, 100);

        canvas.drawBitmap(plane, matrix, mPaint);
        canvas.drawPath(path, mPaint);

        canvas.drawBitmap(plane, 0, 0, mPaint);

        invalidate();
    }
}
