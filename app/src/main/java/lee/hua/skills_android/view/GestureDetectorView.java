package lee.hua.skills_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 触摸手势检测view GestureDetector基本使用
 *
 * @author lijie
 */
public class GestureDetectorView extends android.support.v7.widget.AppCompatButton {
    public static final String TAG = "GestureDetectorView";
    private GestureDetector gestureDetector;
    private IGestureCallback callback;

    public GestureDetectorView(Context context) {
        super(context);
        init();
    }

    public GestureDetectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureDetectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        gestureDetector = new GestureDetector(getContext(), new MyGestureDetector());
        callback = new IGestureCallback() {
            @Override
            public void onGestureTouchEvent(String event) {
                Log.e(TAG, event);
            }

            @Override
            public void onDoubleClick() {
            }
        };
    }

    /**
     * 设置触摸手势回调对象
     *
     * @param callback 回调对象
     */
    public void setCallback(IGestureCallback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 触摸手势检测器
     */
    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        MyGestureDetector() {
            super();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            callback.onGestureTouchEvent("onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            callback.onGestureTouchEvent("onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            callback.onGestureTouchEvent("onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            callback.onGestureTouchEvent("onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            callback.onGestureTouchEvent("onShowPress");
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            callback.onGestureTouchEvent("onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            callback.onGestureTouchEvent("onDoubleTap");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            callback.onDoubleClick();
            callback.onGestureTouchEvent("onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            callback.onGestureTouchEvent("onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            callback.onGestureTouchEvent("onContextClick");
            return super.onContextClick(e);
        }
    }

    /**
     * 触摸手势回调接口
     */
    public interface IGestureCallback {
        /**
         * 触摸事件回调
         *
         * @param event 触摸事件名称
         */
        void onGestureTouchEvent(String event);

        /**
         * 双击事件回调
         */
        void onDoubleClick();
    }
}
