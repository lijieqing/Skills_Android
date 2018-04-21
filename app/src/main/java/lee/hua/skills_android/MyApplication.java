package lee.hua.skills_android;

import android.app.Application;

import org.xutils.x;

/**
 * @author lijie
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
