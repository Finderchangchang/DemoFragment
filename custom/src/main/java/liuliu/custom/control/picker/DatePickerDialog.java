package liuliu.custom.control.picker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import liuliu.custom.R;
import liuliu.custom.control.edittext.SpinnerEditText;

/**
 * Created by Administrator on 2015/11/16.
 */
public class DatePickerDialog implements DatePicker.OnDateChangedListener {
    private DatePicker datePicker;
    private AlertDialog dig;
    private String data;
    private String initData;
    private Activity activity;

    /**
     * 日期弹出选择框构造函数调用的父activity
     * 初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DatePickerDialog(Activity activity, String initDataTime) {
        this.activity = activity;
        this.initData = initDataTime;
    }

    public AlertDialog datePickerDialog(final SpinnerEditText inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.my_datapicker, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.my_datapicker);
        init(datePicker);
        dig = new AlertDialog.Builder(activity).setTitle(initData).setView(dateTimeLayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputDate.setCenterTxt(data);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //inputDate.setText("");
            }
        }).show();
        onDateChanged(null, 0, 0, 0);
        return dig;
    }

    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == datePicker || "".equals(initData))) {
            calendar = this.getCalendarByInitData(initData);
        } else {
            initData = calendar.get(calendar.YEAR) + "年" +
                    calendar.get(calendar.MONTH) + "月" +
                    calendar.get(calendar.DAY_OF_MONTH) + "日";
        }
        datePicker.init(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH), this);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //获得日历实例
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        data = sdf.format(calendar.getTime());
        dig.setTitle(data);

    }

    //实现将初始日期时间2012年07月11日拆分成年月日，并赋值给calender
    private Calendar getCalendarByInitData(String initDate) {
        Calendar calendar = Calendar.getInstance();
        if (initDate.equals("")) {
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            initDate = ss.format(new Date());
        }
        String year = initDate.substring(0, 4);
        String month = initDate.substring(5, 7);
        String day = initDate.substring(8, 10);
//        String year = spliteString(initDate, "年", "0", "4");//日期
//        //String monthandday = spliteString(initDate, "月", "index", "front");//月日
//        String month = spliteString(initDate, "月", "6", "front");
//        String day = spliteString(initDate, "日", "index", "front");

        int iyear = Integer.valueOf(year.trim()).intValue();
        int imonth = Integer.valueOf(month.trim()).intValue() - 1;
        int iday = Integer.valueOf(day.trim()).intValue();
        //将初始日期时间当前日期
        calendar.set(iyear, imonth, iday);
        return calendar;
    }

    /**
     * 截取子串
     *
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexorlast
     * @param frontorback
     * @return
     */
    public static String spliteString(String srcStr, String pattern, String indexorlast, String frontorback) {
        String result = "";
        int loc = -1;
        if (indexorlast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern);//取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern);//最后一个匹配串的位置
        }
        if (frontorback.equalsIgnoreCase("front")) {
            if (loc != -1) {
                result = srcStr.substring(0, loc);//截取子串
            }
        } else {
            if (loc != -1) {
                result = srcStr.substring(loc + 1, srcStr.length());//截取子串
            }
        }

        return result;
    }
}
