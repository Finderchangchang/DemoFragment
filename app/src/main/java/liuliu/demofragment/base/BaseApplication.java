package liuliu.demofragment.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import liuliu.custom.control.toast.RadiusToast;


/**
 * 作者:柳伟杰 邮件:1031066280@qq.com
 * 创建时间:15/6/21 下午10:13
 * 描述:
 */
public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    private static Context context;

    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = getApplicationContext();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static Context getContext() {
        return context;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    //系统处于资源匮乏的状态
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 短弹出
     *
     * @param text 显示内容
     */
    public static void toastShort(String text) {
        RadiusToast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长弹出
     *
     * @param text 显示内容
     */
    public static void toastLong(String text) {
        RadiusToast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 获得设备信息相关方法
     *
     * @return TelephonyManager设备信息管理类
     */
    public static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
    }

    public static void intentPost(Class cla, onPutListener listener) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setClass(context, cla);
        if (listener != null) {
            listener.post(intent);
        }
        context.startActivity(intent);
    }

    public interface onPutListener {
        void post(Intent intent);
    }

    public static void intentPost(Class cla) {
        intentPost(cla, null);
    }
}