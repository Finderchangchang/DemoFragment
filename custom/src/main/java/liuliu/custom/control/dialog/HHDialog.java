package liuliu.custom.control.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import liuliu.custom.R;

/**
 * 自定义Dialog样式
 * 作者：柳伟杰 on 2016/4/9 15:21
 * 邮箱：1031066280@qq.com
 */
public class HHDialog extends Dialog {
    ImageView close_iv;
    ImageView center_sure_iv;
    TextView normal_big_tv;
    TextView normal_small_tv;
    Button sure_click_btn;
    LinearLayout all_bottom_ll;
    OnCloseListener mCloseListener;
    OnResultListener mSureListener;
    //Utils.setDialogWidth(this.getWindowManager().getDefaultDisplay().getWidth(), dialog);设置dialog宽度

    public HHDialog(Context context) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.hh_dialog, null);
        close_iv = (ImageView) mView.findViewById(R.id.close_iv);
        all_bottom_ll = (LinearLayout) mView.findViewById(R.id.all_bottom_ll);
        center_sure_iv = (ImageView) mView.findViewById(R.id.center_sure_iv);
        normal_big_tv = (TextView) mView.findViewById(R.id.normal_big_tv);
        normal_small_tv = (TextView) mView.findViewById(R.id.normal_small_tv);
        sure_click_btn = (Button) mView.findViewById(R.id.sure_click_btn);
        sure_click_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSureListener != null) {
                    mSureListener.onClick();
                }
            }
        });
        close_iv.setOnClickListener(new View.OnClickListener() {//点击右上角关闭按钮触发事件
            @Override
            public void onClick(View v) {
                if (mCloseListener != null) {
                    mCloseListener.onClick();
                }
            }
        });
        setBottomVisible();
        super.setContentView(mView);
    }

    /**
     * 设置中间大字的内容
     *
     * @param val 需要显示的内容
     */
    public void setNormalBig(String val) {
        normal_big_tv.setText(val);
    }

    /**
     * 设置中间小字的内容
     *
     * @param val 需要显示的内容
     */
    public void setNormalSmall(String val) {
        normal_small_tv.setVisibility(View.VISIBLE);
        normal_small_tv.setText(val);
    }

    /**
     * 右上角关闭按钮点击事件
     */
    public interface OnCloseListener {
        void onClick();
    }

    /**
     * 底部中间确定按钮点击事件效果
     */
    public interface OnResultListener {
        void onClick();
    }

    /**
     * 设置关闭按钮点击事件
     *
     * @param mCloseListener
     */
    public void setCloseListener(OnCloseListener mCloseListener) {
        this.mCloseListener = mCloseListener;
    }

    /**
     * 设置中间按钮文本信息
     *
     * @param val
     */
    public void setSureText(String val) {
        sure_click_btn.setText(val);
        sure_click_btn.setVisibility(View.VISIBLE);
        setBottomVisible();
    }

    /**
     * 设置中间确定按钮点击事件
     *
     * @param mSureListener 点击接口
     */
    public void setSureListener(OnResultListener mSureListener) {
        this.mSureListener = mSureListener;
        sure_click_btn.setVisibility(View.VISIBLE);
        setBottomVisible();
    }

    /**
     * 设置中间确定按钮点击事件
     *
     * @param val           中间确定按钮文本
     * @param mSureListener 点击接口
     */
    public void setSureListener(String val, OnResultListener mSureListener) {
        this.mSureListener = mSureListener;
        sure_click_btn.setText(val);
        sure_click_btn.setVisibility(View.VISIBLE);
        setBottomVisible();
    }

    /**
     * 设置底部内容显示隐藏
     * 底部俩组件都是隐藏，将外部组件也隐藏
     * 否则显示外部组件
     */
    public void setBottomVisible() {
        if (sure_click_btn.getVisibility() == View.GONE && normal_small_tv.getVisibility() == View.GONE) {
            all_bottom_ll.setVisibility(View.GONE);
        } else {
            all_bottom_ll.setVisibility(View.VISIBLE);
        }
    }
}