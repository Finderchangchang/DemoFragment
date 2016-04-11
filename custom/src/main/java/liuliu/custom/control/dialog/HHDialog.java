package liuliu.custom.control.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    public HHDialog(Context context) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.hh_dialog, null);
        close_iv = (ImageView) mView.findViewById(R.id.close_iv);
        center_sure_iv = (ImageView) mView.findViewById(R.id.center_sure_iv);
        normal_big_tv = (TextView) mView.findViewById(R.id.normal_big_tv);
        normal_small_tv = (TextView) mView.findViewById(R.id.normal_small_tv);
        sure_click_btn = (Button) mView.findViewById(R.id.sure_click_btn);
        super.setContentView(mView);
    }
}
