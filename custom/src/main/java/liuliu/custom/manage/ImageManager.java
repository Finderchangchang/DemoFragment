package liuliu.custom.manage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.InputStream;

/**
 * 图片管理类
 * 作者：柳伟杰 on 2016/4/15 10:48
 * 邮箱：1031066280@qq.com
 */
public class ImageManager {
    /**
     * 检测Android设备是否支持摄像机
     *
     * @param mContext 系统常量
     * @return true, 支持。false，不支持
     */
    public boolean checkCamera(Context mContext) {
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 本地图片转Bitmap
     * 省内存的方式读取本地资源的图片
     *
     * @param context 系统常量
     * @param resid   本地图片地址
     * @return
     */
    public static Bitmap readBitmap(Context context, int resid) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resid);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 将Byte转化为Bitmap
     *
     * @param str Byte字节数组
     * @return Bitmap数据
     */
    public static Bitmap setByteToBitmap(String str) {
        try {
            byte[] buffer = Base64.decode(str.getBytes(), Base64.DEFAULT);
            if (buffer != null && buffer.length > 0) {
                return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
