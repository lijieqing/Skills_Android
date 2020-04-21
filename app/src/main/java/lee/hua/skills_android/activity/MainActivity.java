package lee.hua.skills_android.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import lee.hua.skills_android.R;
import lee.hua.skills_android.fragment.AppInfoFragment;
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

    @ViewInject(R.id.tv_content)
    private TextView tvContent;

    @ViewInject(R.id.main_root)
    private DrawerLayout drawerLayout;

    /**
     * 仿时钟view
     */
    private ClockView clockView;
    /**
     * String Tag view 集合
     */
    private StringTagView tagView;
    /**
     * 画板
     */
    private DrawView drawView;
    /**
     * 搜索动画
     */
    private PathSearch pathSearch;
    /**
     * 旋转的飞机
     */
    private PathCut pathCut;
    /**
     * 异形区域点击
     */
    private RegionClickView regionClick;
    /**
     * 水波纹效果
     */
    private MeshView meshView;
    /**
     * 正弦运动动画
     */
    private SineWaveView sineWaveView;
    /**
     * 应用展示 Fragment
     */
    private AppInfoFragment appInfoFragment;

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

        nav.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (content.getChildCount() > 1) {
            //移除所有布局
            for (int i = 0; i < content.getChildCount(); i++) {
                if (i == 0) {
                    continue;
                }
                content.removeViewAt(i);
            }
        }
        if (appInfoFragment!=null && appInfoFragment.isAdded()) {
            getFragmentManager().beginTransaction().remove(appInfoFragment).commit();
        }
        switch (itemId) {
            case R.id.transition_animation:
                Intent intent = new Intent(this, TransitionAnimActivity.class);
                Pair<View, String> share = Pair.create((View) tvContent, "tv_content");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        share).toBundle());
                break;
            case R.id.gesture_simple:
                Intent gsIntent = new Intent(this, GestureDetectorActivity.class);
                startActivity(gsIntent);
                break;
            case R.id.view_clock:
                if (clockView == null) {
                    clockView = new ClockView(this);
                }
                content.addView(clockView);
                break;
            case R.id.view_tag:
                if (tagView == null) {
                    tagView = new StringTagView(this);
                    tagView.addTagClickListener(tagClickListener);
                }
                content.addView(tagView);
                break;
            case R.id.view_sine:
                if (sineWaveView == null) {
                    sineWaveView = new SineWaveView(this);
                }
                content.addView(sineWaveView);
                break;
            case R.id.view_mesh:
                if (meshView == null) {
                    meshView = new MeshView(this);
                }
                content.addView(meshView);
                break;
            case R.id.view_draw:
                if (drawView==null){
                    drawView = new DrawView(this);
                }
                content.addView(drawView);
                break;
            case R.id.path_search:
                if (pathSearch==null){
                    pathSearch = new PathSearch(this);
                }
                content.addView(pathSearch);
                break;
            case R.id.path_region:
                if (regionClick==null){
                    regionClick = new RegionClickView(this);
                }
                content.addView(regionClick);
                break;
            case R.id.path_rotate:
                if (pathCut==null){
                    pathCut = new PathCut(this);
                }
                content.addView(pathCut);
                break;
            case R.id.show_app_info:
                appInfoFragment = new AppInfoFragment();
                appInfoFragment.setMode(AppInfoFragment.ALL_APP);
                getFragmentManager().beginTransaction().add(R.id.main_content, appInfoFragment).commit();
                break;
            case R.id.show_sys_app_info:
                appInfoFragment = new AppInfoFragment();
                appInfoFragment.setMode(AppInfoFragment.SYS_APP);
                getFragmentManager().beginTransaction().add(R.id.main_content, appInfoFragment).commit();
                break;
            case R.id.show_third_app_info:
                appInfoFragment = new AppInfoFragment();
                appInfoFragment.setMode(AppInfoFragment.THIRD_APP);
                getFragmentManager().beginTransaction().add(R.id.main_content, appInfoFragment).commit();
                break;
            default:
                Log.e("MainActivity", "未指定 ID");
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }
}
