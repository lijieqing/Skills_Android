package lee.hua.skills_android.view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RegionClickView extends View {
    int width, height;
    int radius = 30;
    Paint mPaint;
    Path circlePath;
    Region region;

    public RegionClickView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.DKGRAY);
        region = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Region temp = new Region(0, 0, w, h);

        circlePath = new Path();
        circlePath.addCircle(w / 2, h / 2, w / 6, Path.Direction.CW);
        circlePath.addCircle(w / 2, h / 2, w / 6 * 0.7f, Path.Direction.CCW);
        region.setPath(circlePath, temp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (region.contains(x, y)) {
                    Toast.makeText(getContext(), "点击选中区域", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(circlePath, mPaint);
    }
}
