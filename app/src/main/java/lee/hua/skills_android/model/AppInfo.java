package lee.hua.skills_android.model;

import android.graphics.drawable.Drawable;

/**
 * @author lijie
 */
public class AppInfo {
    private String appName;
    private Drawable appIcon;
    private String pkgName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
