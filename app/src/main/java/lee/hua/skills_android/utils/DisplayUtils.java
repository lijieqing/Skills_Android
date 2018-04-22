package lee.hua.skills_android.utils;

import android.content.Context;

public final class DisplayUtils {
    private DisplayUtils(){}

    /**
     * px 转 dp
     * @param context context
     * @param px px
     * @return dp
     */
    public static float px2dp(Context context,float px){
        float scale = context.getResources().getDisplayMetrics().density;
        return px/scale+0.5f;
    }

    /**
     * dp 转 px
     * @param context context
     * @param dp dp
     * @return px
     */
    public static float dp2px(Context context,float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return dp*scale+0.5f;
    }
    /**
     * px 转 sp
     * @param context context
     * @param px pixel
     * @return sp
     */
    public static float px2sp(Context context,float px){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scale+0.5f;
    }
    /**
     * dp 转 px
     * @param context context
     * @param sp sp
     * @return px
     */
    public static float sp2px(Context context,float sp){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp*scale+0.5f;
    }
}
