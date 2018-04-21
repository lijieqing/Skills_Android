package lee.hua.skills_android.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lee.hua.skills_android.R;
import lee.hua.skills_android.view.ClockView;
import lee.hua.skills_android.view.StringTagView;

/**
 * @author lijie
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.nav)
    private NavigationView nav;

    @ViewInject(R.id.main_content)
    private FrameLayout content;

    @ViewInject(R.id.stv)
    private StringTagView stv;

    private ClockView clockView;
    private StringTagView tagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initView();
        nav.setNavigationItemSelectedListener(this);
    }
    private void initView(){
        clockView = new ClockView(this);
        tagView = new StringTagView(this);
        stv.addTagClickListener(new StringTagView.TagClickListener() {
            @Override
            public void onTagClickListener(TextView textView) {
                Toast.makeText(MainActivity.this,textView.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //移除所有布局
        content.removeAllViews();
        switch (itemId) {
            case R.id.view_clock:
                content.addView(clockView);
                break;
            case R.id.view_tag:
                content.addView(tagView);
                break;
            default:
                Log.e("MainActivity", "未指定 ID");
                break;
        }
        return true;
    }
}
