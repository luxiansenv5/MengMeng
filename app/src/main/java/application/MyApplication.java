package application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * Created by 程和 on 2016/10/14.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
