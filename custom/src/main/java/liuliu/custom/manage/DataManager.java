package liuliu.custom.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间管理类
 * 作者：柳伟杰 on 2016/4/14 16:43
 * 邮箱：1031066280@qq.com
 */
public class DataManager {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将String的time转化为Date
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Date stringToDate(String time) {
        try {
            return sdf.parse(time);
        } catch (java.text.ParseException e) {
            return null;
        }
    }


    /**
     * 将Date转化为String
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return sdf.format(date);
    }

    /**
     * 将带T的Date转化为String
     *
     * @param date 需要转化的Date数据
     * @return 带T的String时间
     */
    public static String dateToStringT(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date).replace(" ", "T");
    }

    /**
     * 加载当前时间。
     * 1.同一年的显示格式 05-11  07:45
     * 2.前一年或者更多格式 2015-11-12
     *
     * @param hanzi 时间格式为汉字：true
     * @param old   yyyy-MM-dd HH:mm:ss
     * @return 需要显示的处理结果
     */
    public static String loadTime(boolean hanzi, String old) {
        String old_year = old.substring(0, 4);//获得old里面的年
        String now_year = new SimpleDateFormat("yyyy").format(new Date()).substring(0, 4);//获得当前的年
        if (old_year.equals(now_year)) {//两者为同一年
            String time = old.substring(5, 16);
            if (hanzi) {
                time = time.replace("月", "-");
                time = time.replace("日", " ");
            }
            return time;
        } else {
            String time = old.substring(0, 10);
            if (hanzi) {
                time = time.replace("年", "-");
                time = time.replace("月", "-");
                time = time.replace("日", " ");
            }
            return time;
        }
    }

    /**
     * 加载时间并根据一定格式显示
     *
     * @param time yyyy-MM-dd HH:mm:ss
     * @return 处理完以后的时间
     */
    public static String loadHanziTime(String time) {
        return loadTime(true, time);
    }

    /**
     * 加载时间，默认格式
     *
     * @param time yyyy-MM-dd HH:mm:ss
     * @return 处理完以后的时间
     */
    public static String loadNormalTime(String time) {
        return loadTime(false, time);
    }
}
