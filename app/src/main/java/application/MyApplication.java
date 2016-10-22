package application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

import io.rong.imkit.RongIM;


/**
 * Created by 程和 on 2016/10/14.
 */
public class MyApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
