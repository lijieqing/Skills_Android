package lee.hua.skills_android.activity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import lee.hua.skills_android.R;

public class LonelyActivity extends AppCompatActivity {
    private int SIZE = 30;
    private RelativeLayout rl;
    private GridView gv;
    private List<Map<String, Object>> list_data;
    private Timer timer;
    private ImageView ivProjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lonely);
        initView();
    }

    private void initView() {
        rl = findViewById(R.id.rl_bg);
        rl.setBackgroundColor(Color.DKGRAY);
        gv = findViewById(R.id.gv_factory);
        ivProjector = findViewById(R.id.iv_projector);
        initData();
        initAnim();
    }

    private void initAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1800, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ivProjector.setX(value);
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.start();
    }

    private void initData() {
        if (Build.DEVICE.equals("batman") || Build.DEVICE.equals("rainman")) {
            SIZE = 30;
        } else {
            SIZE = 21;
        }
        list_data = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", R.drawable.file_folder);
            list_data.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list_data, R.layout.grid_item, new String[]{"image"}, new int[]{R.id.iv_factory});
        gv.setAdapter(simpleAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gv.setSelection((int) ((SIZE - 1) * Math.random()));
                        }
                    });
                }
            }, 1000, 100);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
