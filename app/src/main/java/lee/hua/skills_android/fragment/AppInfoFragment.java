package lee.hua.skills_android.fragment;

import android.app.ListFragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lee.hua.skills_android.R;
import lee.hua.skills_android.model.AppInfo;

/**
 * @author lijie
 */
public class AppInfoFragment extends ListFragment {
    /**
     * 所有的应用
     */
    public static final int ALL_APP = 0;
    /**
     * 系统应用
     */
    public static final int SYS_APP = 1;
    /**
     * 三方应用
     */
    public static final int THIRD_APP = 2;
    private List<AppInfo> appInfoList;
    private AppInfoAdapter appInfoAdapter;
    private int mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appInfoList = new ArrayList<>();
        appInfoAdapter = new AppInfoAdapter();
        setListAdapter(appInfoAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateAppInfo(mode);
    }

    /**
     * 设置需要显示的 App 类型
     * @param mode
     */
    public void setMode(int mode){
        this.mode = mode;
    }

    /**
     * 获取指定模式的 App 信息
     * @param mode 模式
     */
    private void updateAppInfo(int mode) {
        appInfoList.clear();
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> tempList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        switch (mode) {
            case ALL_APP:
                for (ApplicationInfo app : tempList) {
                    appInfoList.add(wrapAppInfo(app, pm));
                }
                break;
            case SYS_APP:
                for (ApplicationInfo app : tempList) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfoList.add(wrapAppInfo(app, pm));
                    }
                }
                break;
            case THIRD_APP:
                for (ApplicationInfo app : tempList) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        appInfoList.add(wrapAppInfo(app, pm));
                    } else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        appInfoList.add(wrapAppInfo(app, pm));
                    }
                }
                break;
            default:
                for (ApplicationInfo app : tempList) {
                    appInfoList.add(wrapAppInfo(app, pm));
                }
                break;
        }

        appInfoAdapter.notifyDataSetChanged();

    }

    private AppInfo wrapAppInfo(ApplicationInfo app, PackageManager pm) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(app.loadLabel(pm).toString());
        appInfo.setPkgName(app.packageName);
        appInfo.setAppIcon(app.loadIcon(pm));
        return appInfo;
    }

    class AppInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return appInfoList.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return appInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = View.inflate(getActivity(), R.layout.app_info_item, null);
                holder.icon = convertView.findViewById(R.id.app_icon);
                holder.name = convertView.findViewById(R.id.app_name);
                holder.pkg = convertView.findViewById(R.id.app_pkg);

                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }
            String name = getItem(position).getAppName();
            String pkg = getItem(position).getPkgName();
            Drawable icon = getItem(position).getAppIcon();
            holder.name.setText(name);
            holder.pkg.setText(pkg);
            if (icon != null) {
                holder.icon.setImageDrawable(icon);
            }
            return convertView;
        }

        class MyHolder {
            ImageView icon;
            TextView name;
            TextView pkg;
        }
    }
}
