package liuliu.custom.method;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import liuliu.custom.R;
import liuliu.custom.control.toast.RadiusToast;
import liuliu.custom.method.xml.Jxml;

public class Utils {
    static Context mContext;

    public Utils(Context context) {
        mContext = context;
    }

    public void setText(TextView textView, String val) {
        setText(textView, val, "");
    }

    /**
     * 截取指定字符串并添加并在后面添加...
     *
     * @param val    截取前的字符串
     * @param length 截取字符长度
     * @return 处理之后的结果
     */
    public static String cutStringWithDian(String val, int length) {
        return val.substring(0, length) + "...";
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //  获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 加载当前时间。
     * 1.同一年的显示格式 05-11  07:45
     * 2.前一年或者更多格式 2015-11-12
     *
     * @param old
     * @return 需要显示的处理结果
     */
    public static String loadTime(String old) {
        String old_year = old.substring(0, 4);//获得old里面的年
        String now_year = new SimpleDateFormat("yyyy").format(new Date()).substring(0, 4);//获得当前的年
        if (old_year.equals(now_year)) {//两者为同一年
            return old.substring(5, 16);
        } else {
            return old.substring(0, 10);
        }
    }

    /**
     * 获得当前系统时间
     *
     * @return 2015-11-12 04：35：12
     */
    public static String getRealTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 证件号码进行sha1加密
     *
     * @param val
     * @return
     */
    public String stringToSha1(String val) {
        String s = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(val.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();

            for (int i = 0; i < digest.length; i++) {
                String sp = Integer.toHexString(digest[i]);
                if (sp.length() > 1) {
                    s += sp.substring(sp.length() - 2, sp.length());
                } else {
                    s += sp;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    //当前坐标
    public Location getLocation() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        Location location = null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = new Location(LocationManager.GPS_PROVIDER);
                location.setLatitude(0);
                location.setLongitude(0);
                location.setAltitude(0);
            }
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 10,
                    locationListener);
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) {
                location = new Location(LocationManager.NETWORK_PROVIDER);
                location.setLatitude(0);
                location.setLongitude(0);
                location.setAltitude(0);
            }
        }
        return location;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                // TODO Auto-generated method stub
                double latitude = location.getLatitude(); // 纬度
                double longitude = location.getLongitude(); // 经度
                Log.i("" + latitude, "" + longitude);
            } else {
                System.out.println("空！！");
            }
        }
    };

    //得到手机的imei
    public static String getImei(Context context) {
        // return "357897047649338";
        return ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    //date(20150101) ==date
    public static Date changeDate(String datestr) {
        datestr = datestr.substring(5, 14);
        return StringToDate(datestr);
    }

    //2015年05月09日
    public static String changDatetoGeshi(String date) {
        date = date.replace("年", "-");
        date = date.replace("月", "-");
        date = date.replace("日", " ");
        return date;
    }

    public String saveTmpFile(String text) {
        String path = "";
        if (text != null && text.length() > 0) {
            try {
                File temp = new File("/sdcard/aa.txt");
                OutputStream fstream = new FileOutputStream(temp);
                fstream.write(text.getBytes());
                fstream.close();
                path = temp.getAbsolutePath();
            } catch (Exception e) {
            }
        }
        return path;
    }

    public static String URLEncodeImage(String text) {
        if (Utils.isEmptyString(text))
            return "";

        return URLEncoder.encode(text);
    }

    public void setText(TextView textView, String val, String def_val) {
        if (val != null) {
            textView.setText(val);
        } else {
            if (def_val.equals("")) {
                def_val = "无";
            }
            textView.setText(def_val);
        }
    }

    /**
     * 设置默认显示内容
     *
     * @param val     需要显示的内容
     * @param def_val 如果为空，默认显示的内容
     * @return 处理后的结果
     */
    public static String setDefault(String val, String def_val) {
        if (null == val || ("").equals(val) || ("null").equals(val)) {
            if (("").equals(def_val)) {
                return "无";
            } else {
                return def_val;
            }
        } else {
            return val;
        }
    }

    //省内存的方式读取本地资源的图片
    public static Bitmap readBitmap(Context context, int resid) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resid);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    private long exitTime = 0;

    public interface OnExitListener {
        void toast();

        void finish();
    }

    //包名+model名
    public static Object getBean(String className) throws Exception {
        Class cls = null;
        try {
            cls = Class.forName("liuliu.throughwaring.model." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("类错误");
        }
        Constructor[] cons = null;
        try {
            cons = cls.getConstructors();//得到所有构造器
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("构造器错误");
        }
        //如果上面没错，就有构造方法
        Constructor defcon = cons[0];//得到默认构造器，第0个事默认构造器，无参构造方法
        Object obj = defcon.newInstance();//实例化，得到一个对象
        return obj;
    }

    //创建的model对象，字段名，字段值
    public static void setProperty(Object bean, String propertyName, Object propertyValue) throws Exception {
        Class cls = bean.getClass();
        Method[] methos = cls.getMethods();//得到所有的方法
        for (Method m : methos) {
            if (m.getName().equalsIgnoreCase("set" + propertyName)) {
                //找到方法就注入
                m.invoke(bean, propertyValue);
                break;
            }
        }
    }

    //创建的model对象，字段名，字段值
    public static void setPropertyInt(Object bean, String propertyName, Object propertyValue) throws Exception {
        Class cls = bean.getClass();
        Method[] methos = cls.getMethods();//得到所有的方法
        for (Method m : methos) {
            if (m.getName().equalsIgnoreCase("set" + propertyName)) {
                //找到方法就注入
                m.invoke(bean, new Integer(propertyValue.toString()));
                break;
            }
        }
    }

    public static void ToastShort(Context context, String text) {
        RadiusToast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /*自定义加载条*/
    public static Dialog ProgressDialog(Context context, Dialog progressDialog, String val, boolean istrue) {
        progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(istrue);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(val);
        return progressDialog;
    }

    public static String URLEncode(String text) {
        if (isEmptyString(text))
            return "";
        if (text.equals("null"))
            return "";
        return text;
        /*
         * if(Utils.isEmptyString(text)) return "";
		 *
		 * return URLEncoder.encode(text);
		 */
    }

    public void exit(OnExitListener listener) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            listener.toast();
            exitTime = System.currentTimeMillis();
        } else {
            listener.finish();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    public boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public LinearLayout.LayoutParams setMargin(int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                width, height);
        lp.setMargins(left, top, right, bottom);
        return lp;
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*根据String类型的time转化为Date类型*/
    public static Date StringToDate(String time) {
        try {
            return sdf.parse(time);
        } catch (java.text.ParseException e) {
            return null;
        }
    }

    /*将Date转化为String类型*/
    public static String DateToString(Date date) {
        return sdf.format(date);
    }

    /*自定义加载条*/
    public Dialog ProgressDialog(Dialog progressDialog, String val) {
        progressDialog = new Dialog(mContext, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(val);
        return progressDialog;
    }

    public boolean fillTestData() {
        return false;
    }

    public String createGUID() {
        return UUID.randomUUID().toString();
    }

    public double getDouble(TextView view) {
        if (view == null)
            return 0;

        try {
            return Double.parseDouble(view.getText().toString());
        } catch (Exception e) {
        }
        return 0;
    }

    public int getInt(TextView view) {
        if (view == null)
            return 0;

        try {
            return Integer.parseInt(view.getText().toString());
        } catch (Exception e) {
        }

        return 0;
    }

    public int getInt(Spinner view) {
        if (view == null)
            return 0;

        try {
            return view.getSelectedItemPosition();
        } catch (Exception e) {
        }

        return -1;
    }

    public String getString(TextView view) {
        if (view == null)
            return "";

        try {
            return view.getText().toString();
        } catch (Exception e) {
        }

        return "";
    }

    public String getString(Spinner view) {
        if (view == null)
            return "";

        try {
            return view.toString();
        } catch (Exception e) {
        }

        return "";
    }

    public static String Preferences_name = "BULKGASOLINE";

    public static String ReadString(String key) {
        SharedPreferences sp = getSharedPreferences();
        if (sp != null) {
            return sp.getString(key, "");
        } else {
            return "";
        }

    }

    public int getScannerWidth() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    public int getScannerHeight() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    public static void WriteString(String key, String value) {
        SharedPreferences sp = getSharedPreferences();
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int ReadInt(String key) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getInt(key, 0);
    }

    public void WriteInt(String key, int value) {
        SharedPreferences sp = getSharedPreferences();
        // 锟斤拷锟斤拷锟斤拷锟斤拷
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void WriteBoolean(String key, boolean value) {
        SharedPreferences sp = getSharedPreferences();
        // 锟斤拷锟斤拷锟斤拷锟斤拷
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Set<String> ReadStringSet(String key) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getStringSet(key, new LinkedHashSet<String>());
    }

    public void WriteStringSet(String key,
                               Set<String> value) {
        SharedPreferences sp = getSharedPreferences();
        // 锟斤拷锟斤拷锟斤拷锟斤拷
        Editor editor = sp.edit();
        editor.putStringSet(key, value);

        editor.commit();
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public void hideIM() {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) mContext).getCurrentFocus();
        if (v == null)
            imm.hideSoftInputFromWindow(null, 0);
        else
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public Uri startCamra(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 锟斤拷锟斤拷锟斤拷锟角诧拷锟斤拷一锟斤拷锟斤拷锟捷ｏ拷ContentValues锟斤拷锟斤拷锟斤拷希锟斤拷锟斤拷锟斤拷锟斤拷录锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷息
        // 锟斤拷些锟斤拷锟捷碉拷锟斤拷锟斤拷锟窖撅拷锟斤拷为锟斤拷锟斤拷锟斤拷MediaStore.Images.Media锟斤拷,锟叫的存储锟斤拷MediaStore.MediaColumn锟斤拷锟斤拷
        // ContentValues values = new ContentValues();

        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "testing");
        values.put(MediaStore.Images.Media.DESCRIPTION, "this is description");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Uri uri =
        // context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        // values);
        Uri uri = Uri.fromFile(new File(getDataPath() + "/temp/"
                + System.currentTimeMillis() + ".tmp"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 锟斤拷锟斤拷锟酵斤拷锟侥硷拷锟侥存储锟斤拷式锟斤拷uri指锟斤拷锟斤拷锟斤拷Camera应锟斤拷锟斤拷

        // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷要锟斤拷锟斤拷锟斤拷Camera锟襟，匡拷锟皆凤拷锟斤拷Camera锟斤拷取锟斤拷锟斤拷图片锟斤拷
        // 锟斤拷锟皆ｏ拷锟斤拷锟斤拷使锟斤拷startActivityForResult锟斤拷锟斤拷锟斤拷Camera
        mContext.startActivity(intent);
        return uri;
    }

    public String getDataPath() {
        File defaultStorageFile = Environment.getExternalStorageDirectory();
        return String.format("%s/Gasoline",
                defaultStorageFile.getAbsolutePath());
    }

    public String getDataPath(String name) {
        return String.format("%s/%s", getDataPath(), name);
    }

    public String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 锟斤拷锟斤拷锟街碉拷锟斤拷息
     */
    public boolean loadCodes(String codeName,
                             ArrayList<String> codeKeys, ArrayList<String> codeValues) {
        String xml = ReadString(codeName);
        if (Utils.isEmpty(xml))
            return false;
        return parseCodeXml(xml, codeKeys, codeValues);
    }

    public boolean codesEqual(String codeName,
                              ArrayList<String> codeKeys, ArrayList<String> codeValues) {
        String xml = ReadString(codeName);
        if (Utils.isEmpty(xml))
            return false;
        return parseCodeXml(xml, codeKeys, codeValues);

    }

    public boolean ReadBoolean(String key) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getBoolean(key, false);
    }

    public static SharedPreferences getSharedPreferences() {
        if (mContext != null) {
            return mContext.getSharedPreferences(Preferences_name,
                    Context.MODE_PRIVATE);
        } else {
            return null;
        }
    }

    public boolean parseCodeXml(String content,
                                ArrayList<String> codeKeys, ArrayList<String> codeValues) {
        Jxml xml = new Jxml();
        if (xml.SetDoc(content) && xml.FindElem("InvokeReturn")) {
            xml.IntoElem();
            while (xml.FindElem()) {
                String key, value;
                key = xml.GetTagName();
                if ("Object".compareToIgnoreCase(key) == 0) {
                    codeKeys.clear();
                    codeValues.clear();

                    xml.IntoElem();
                    while (xml.FindElem("CodeModel")) {
                        xml.IntoElem();
                        while (xml.FindElem()) {
                            key = xml.GetTagName();
                            value = xml.GetData();
                            if (key.compareToIgnoreCase("Key") == 0) {
                                codeKeys.add(value);
                            } else if (key.compareToIgnoreCase("Value") == 0) {
                                codeValues.add(value);
                            }
                        }
                        xml.OutOfElem();
                    }
                    xml.OutOfElem();
                } else {
                    value = xml.GetData();
                }
            }
            xml.OutOfElem();

            return true;
        }

        return false;
    }

    public void writeCodes(String codeName, String xml) {
        WriteString(codeName, xml);
    }

    public boolean parseOperatorXml(String content,
                                    ArrayList<String> ids, ArrayList<String> values) {
        Map<String, String> data = new HashMap<String, String>();
        Jxml xml = new Jxml();
        if (xml.SetDoc(content) && xml.FindElem("InvokeReturn")) {
            xml.IntoElem();
            while (xml.FindElem()) {
                String key, value;
                key = xml.GetTagName();
                if ("Object".compareToIgnoreCase(key) == 0) {
                    ids.clear();
                    values.clear();

                    xml.IntoElem();
                    while (xml.FindElem("EmployeeModel")) {
                        xml.IntoElem();
                        while (xml.FindElem()) {
                            key = xml.GetTagName();
                            if (key.compareToIgnoreCase("EmployeeId") == 0) {
                                value = xml.GetData();
                                ids.add(value);
                            } else if (key.compareToIgnoreCase("EmployeeName") == 0) {
                                value = xml.GetData();
                                values.add(value);
                            }
                        }
                        xml.OutOfElem();
                    }
                    xml.OutOfElem();
                } else {
                    value = xml.GetData();
                    data.put(key, value);
                }
            }
            xml.OutOfElem();

            return true;
        }

        return false;
    }

    public String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public String getCSTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date()).replace(" ", "T");
    }

    public static String encodeImageView(ImageView imageview) {
        String imageString = "";
        try {
            imageview.setDrawingCacheEnabled(true);
            Bitmap bitmap = imageview.getDrawingCache();
            imageString = encodeBitmap(bitmap);
            imageview.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageString;
    }

    public static String encodeBitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
                    .trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap getBitmapByte(String str) {
//        Bitmap bitmap;
//        byte[] s = Base64.encode(str.getBytes(), Base64.DEFAULT);
//        bitmap = BitmapFactory.decodeByteArray(s, 0, s.length);
//        return bitmap;
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

    public String XMlEncode(String strData) {
        if (strData == null)
            return "";

        strData = strData.replace("&", "&amp;");
        strData = strData.replace("<", "&lt;");
        strData = strData.replace(">", "&gt;");
        strData = strData.replace("&apos;", "&apos;");
        strData = strData.replace("\"", "&quot;");

        return strData;
    }

    public String URLEncoder(String strData) {
        if (strData == null)
            return "";

        return URLEncoder.encode(strData);
    }

    public String[] LinkedSetToArray(LinkedHashSet<String> datas) {
        String[] array = new String[datas.size()];
        int index = 0;
        for (Iterator<String> iter = datas.iterator(); iter.hasNext(); ) {
            array[index] = (String) iter.next();
            index++;
        }
        return array;
    }

    public boolean saveAssetsFile(AssetManager assetManager,
                                  String strName, String strTarget) {
        boolean bSucc = false;
        final int BUF_SIZE = 10124;
        try {
            InputStream is = assetManager.open(strName);
            if (is == null)
                return false;
            File fOut = new File(strTarget);
            FileOutputStream ofs = new FileOutputStream(fOut);
            if (is != null && ofs != null) {
                byte[] bBuf = new byte[BUF_SIZE];
                int nRead, length = BUF_SIZE;
                while ((nRead = is.read(bBuf, 0, length)) > 0) {
                    ofs.write(bBuf, 0, nRead);
                }
                ofs.close();
                is.close();
                bSucc = true;
            }
        } catch (Exception e) {
        }
        return bSucc;
    }

    public String getTempImagePath() {

        String path = Environment.getExternalStorageDirectory() + "/gasoline/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();

        return path + "temp.image";
    }

    public Bitmap decodeBitmapFromFile(String filePath,
                                       int maxNumOfPixels) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, maxNumOfPixels);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public int computeSampleSize(BitmapFactory.Options options,
                                 int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private int computeInitialSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private Bitmap previewBitmap = null;

    public void setPreviewBitmap(Bitmap bitmap) {
        previewBitmap = bitmap;
    }

    public Bitmap getPreviewBitmap() {
        return previewBitmap;
    }

    public static boolean isEmptyString(String str) {
        return (str == null || str.length() == 0);
    }

    public Boolean getJsonBoolean(JSONObject object, String name) {
        try {
            return object.getBoolean(name);
        } catch (JSONException e) {
            return false;
        }
    }

    public static String getJsonString(JSONObject object, String name) {
        try {
            return object.getString(name);
        } catch (JSONException e) {
            return "";
        }
    }

    public int getJsonInt(JSONObject object, String name) {
        try {
            return object.getInt(name);
        } catch (JSONException e) {
            return 0;
        }
    }

    public static String getJsonDate(JSONObject object, String name) {
        try {
            return getDateString(object.getString(name));
        } catch (JSONException e) {
            return "";
        }
    }

    public static String getDateString(String text) {
        // /Date(1361431509843)/
        try {
            if (!isEmptyString(text)) {
                text = text.replace("/", "");
                text = text.replace("\\", "");
                text = text.replace("Date", "");
                text = text.replace("(", "");
                text = text.replace(")", "");
                SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                return formatter.format(new Date(Long.valueOf(text)));
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 获得当前系统时间
     *
     * @return 格式为yyyyMMdd的时间
     */
    public static String getTodayString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    /**
     * 获得当前系统完整时间
     *
     * @return 返回完整的时间
     */
    public static String getTodayFullString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    public interface putListener {
        void put(Intent intent);
    }

    public void IntentPost(Class cla, putListener listener) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setClass(mContext, cla);
        if (listener != null) {
            listener.put(intent);
        }
        mContext.startActivity(intent);
    }

    public void IntentPost(Class cla) {
        IntentPost(cla, null);
    }

    //intent=getIntent();name=标识符
    public Object IntentGet(Intent intent, String name) {
        return intent.getStringExtra(name);
    }

    //鑾峰緱鎵嬫満璁惧鐨勭浉鍏充俊鎭�
    //鏉冮檺<uses-permission android:name="android.permission.READ_PHONE_STATE" />
    public TelephonyManager getTelephonyManager() {
        return (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
    }


    public void ToastShort(String text) {
        RadiusToast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    //闀挎樉绀鸿嚜瀹氫箟Toast寮瑰嚭妗�
    public void ToastLong(String text) {
        RadiusToast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 检测Android设备是否支持摄像机
     */
    public boolean checkCameraDevice() {
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public static Bitmap getimage(Context contxt, String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        Bitmap header = BitmapFactory.decodeResource(contxt.getResources(), R.mipmap.person_header);

        float hh = header.getHeight();//这里设置高度为800f
        float ww = header.getWidth();//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static boolean checkBluetooth(Activity context, int requestCode) {
        /*
         * Intent serverIntent = new Intent(context, DeviceListActivity.class);
		 * context.startActivity(serverIntent); return true;
		 */

        boolean result = true;
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (null != ba) {
            if (!ba.isEnabled()) {
                result = false;
                Intent intent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivityForResult(intent, requestCode);// 或者ba.enable();
                // //同样的关闭WIFi为ba.disable();
            }
        }
        return result;
    }
}
