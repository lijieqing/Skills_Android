package lee.hua.skills_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Window;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import lee.hua.skills_android.R;

/**
 * 过渡动画实现
 *
 * @author lijie
 */
@ContentView(R.layout.activity_transition_anim)
public class TransitionAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Explode());

        x.view().inject(this);
    }
}
