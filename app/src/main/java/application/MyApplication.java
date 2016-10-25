package application;

import android.app.Application;

import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.pojo.User;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * Created by 程和 on 2016/10/14.
 */
public class MyApplication extends Application{

    private  User user=new User(1);//设置默认用户，用户ID为1；

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Dynamic dynamic=new Dynamic("北京");//设置默认地址，用户地址为北京
    public  Dynamic getDynamic(){
        return dynamic;
    }
    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
