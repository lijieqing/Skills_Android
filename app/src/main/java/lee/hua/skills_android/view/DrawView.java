package lee.hua.skills_android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lijie on 2017/10/20.
 * <p>
 * 自定义画板view
 * <p>
 * 事件监听onTouchEvent中的DOWN（按下）、MOVE(滑动)、UP(抬起)三个事件
 * <p>
 * 核心技术细节：需要两个canvas：一个是系统自带，onDraw方法中传入的，无需创建。另一个缓存canvas是用来画cacheBitmap，需要自己创建。
 * <p>
 * 其实，整个view中，cacheBitmap是核心，通过监听触摸事件不停调用刷新方法，系统自带canvas会不停drawPath进行页面刷新，
 * 但是它并不会保存path路径，当抬起后，是不会留下路线的。
 * <p>
 * 而缓存canvas在监听到抬起手事件后，会将Path画到cacheBitMap上，并且重置path
 * <p>
 * 此时系统自带的canvas只需要将cacheBitMap画到自己的界面上就可以实现
 * <p>
 * 所以在抬起事件时，缓存canvas在绘画完cacheBitmap后，此时的inValidate()方法去刷新界面，系统自带的canvas就会将cacheBitmap画出来
 */

public class DrawView extends View {

    float preX;
    float preY;
    public Path path;
    public Paint paint;
    public Bitmap cacheBitmap;
    public Canvas cacheCanvas;

    public DrawView(Context context) {
        super(context);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();

        cacheCanvas.setBitmap(cacheBitmap);

        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setDither(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //lineTo(x,y) 和 quadTo(preX,preY,x,y) 效果是一样的，quadTo用到了贝塞尔曲线显示效果可能更圆滑些
                path.quadTo(preX, preY, x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                //直到检测抬起手势，才会将线画在了cache bitmap上，其他手势只是在canvas上显示轨迹，并未真正画到bitmap上
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
        }

        //每触发一次onTouchEvent事件都会调用此方法，调用此方法会刷新布局，即调用onDraw方法
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //将cacheCanvas上的cacheBitMap画到当前的canvas上
        canvas.drawBitmap(cacheBitmap, 0, 0, paint);

        //滑动时 会实时显示滑动轨迹 此时的drawPatch是在canvas自身的 bitmap上进行，障眼法效果
        canvas.drawPath(path, paint);
    }
}
