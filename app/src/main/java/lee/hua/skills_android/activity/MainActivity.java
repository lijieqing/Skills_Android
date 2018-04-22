package lee.hua.skills_android.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
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
import lee.hua.skills_android.view.DrawView;
import lee.hua.skills_android.view.MeshView;
import lee.hua.skills_android.view.SineWaveView;
import lee.hua.skills_android.view.StringTagView;
import lee.hua.skills_android.view.path.PathCut;
import lee.hua.skills_android.view.path.PathSearch;
import lee.hua.skills_android.view.path.RegionClickView;

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
    private DrawView drawView;
    private PathSearch pathSearch;
    private PathCut pathCut;
    private RegionClickView regionClick;
    private MeshView meshView;
    private SineWaveView sineWaveView;

    private StringTagView.TagClickListener tagClickListener = new StringTagView.TagClickListener() {
        @Override
        public void onTagClickListener(TextView textView) {
            Toast.makeText(MainActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initView();
        nav.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化 view 数据
     */
    private void initView() {
        clockView = new ClockView(this);
        tagView = new StringTagView(this);
        stv.addTagClickListener(tagClickListener);
        tagView.addTagClickListener(tagClickListener);
        drawView = new DrawView(this);
        pathCut = new PathCut(this);
        pathSearch = new PathSearch(this);
        regionClick = new RegionClickView(this);
        meshView = new MeshView(this);
        sineWaveView = new SineWaveView(this);
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
            case R.id.view_sine:
                content.addView(sineWaveView);
                break;
            case R.id.view_mesh:
                content.addView(meshView);
                break;
            case R.id.view_draw:
                content.addView(drawView);
                break;
            case R.id.path_search:
                content.addView(pathSearch);
                break;
            case R.id.path_region:
                content.addView(regionClick);
                break;
            case R.id.path_rotate:
                content.addView(pathCut);
                break;
            default:
                Log.e("MainActivity", "未指定 ID");
                break;
        }
        return true;
    }
}
