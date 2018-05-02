package lee.hua.skills_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import lee.hua.skills_android.R;
import lee.hua.skills_android.view.GestureDetectorView;

/**
 * @author lijie
 */
@ContentView(R.layout.activity_gesture_detector)
public class GestureDetectorActivity extends AppCompatActivity implements GestureDetectorView.IGestureCallback {
    @ViewInject(R.id.textView)
    private TextView textView;

    @ViewInject(R.id.gestureDetectorView)
    private GestureDetectorView gestureDetectorView;
    private List<String> strList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        gestureDetectorView.setCallback(this);
    }

    @Override
    public void onGestureTouchEvent(String event) {
        strList.add(event);

        textView.setText(strList.toString());
    }

    @Override
    public void onDoubleClick() {
        strList.clear();
    }
}
