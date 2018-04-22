package lee.hua.skills_android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import lee.hua.skills_android.R;

/**
 * class 图像扭曲view
 * 重点：通过drawBitmapMesh方法 将bitmap通过网格方式绘制出来
 * 监听触摸事件，根据拉伸曲率改变每个网格顶点的坐标，实现扭曲效果
 */
public class MeshView extends View {

    /**
     * 定义网格的列数 即宽度
     */
    private final int WIDTH = 20;
    /**
     * 定义网格的行数，即高度
     */
    private final int HEIGHT = 20;
    /**
     * 所有顶点的数量
     */
    private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    /**
     * 描述每个顶点坐标的集合，一维数组 表示方式是[x1,y1,x2,y2.....]，所以长度在 网格顶点的数量 上乘 2
     */
    private final float[] verts = new float[COUNT * 2];
    /**
     * 网格顶点坐标，原坐标集合，在初始赋值后数值基本不会改变
     */
    private final float[] orig = new float[COUNT * 2];
    private final Bitmap bitmap;


    public MeshView(Context context) {
        super(context);
        setFocusable(true);
        //获取bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_mesh);

        //测量bitmap的宽高
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();

        //根据位图和网格的比例 来计算坐标
        //循环设置每个网格的顶点坐标
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = (bitmapHeight / HEIGHT) * y;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bitmapWidth * x / WIDTH;
                orig[index * 2] = verts[index * 2] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index++;
            }

        }

        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //param1:使用网格绘制的位图。param2：网格的列数。param3：网格的行数
        //param4:网格顶点坐标集合，指定应该在哪里绘制网格。
        // 数组中至少必须有（meshWidth + 1）*（meshHeight + 1）* 2 + vertOffset值。这个值绝对不能null。
        //param5：绘图前要跳过的顶点元素的数量
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        wrap(event.getX(), event.getY());

        return true;
    }

    /**
     * 网格坐标修改
     * 根据点击事件传入的 cx、cy坐标，对集合中所有的顶点坐标进行修改后，调用界面刷新
     *
     * @param cx event.getX
     * @param cy event.getY
     */
    private void wrap(float cx, float cy) {

        for (int i = 0; i < COUNT * 2; i += 2) {
            //获取侧边长 dx、dy
            float dx = cx - orig[i];
            float dy = cy - orig[i + 1];
            //计算斜边平方
            float dd = dx * dx + dy * dy;
            //开方
            float d = (float) Math.sqrt(dd);
            //计算扭曲度，距离越远扭曲度越小。着实不明白为什么这样算，但是效果还不错
            float pull = 80000 / (dd * d);

            if (pull >= 1) {
                //距离较近，将附近其他坐标变换为当前点击坐标，出现明显扭曲效果
                verts[i] = cx;
                verts[i + 1] = cy;
            } else {
                //距离较远，获取到原坐标后，进行运算，此时值变化非常小，无明显扭曲效果
                verts[i] = orig[i] + pull * dx;
                verts[i + 1] = orig[i + 1] + pull * dy;
            }
            Log.e("MeshView", "i: " + i + " verts: " + verts[i] + " cx:" + cx + " cy:" + cy + " pull:" + (pull >= 1));
        }

        //通知刷新
        invalidate();

    }
}