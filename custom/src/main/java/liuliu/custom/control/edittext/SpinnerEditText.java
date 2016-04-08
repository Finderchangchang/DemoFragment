package liuliu.custom.control.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import liuliu.custom.R;

/**
 * Created by liuliu on 2015/11/16   14:12
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class SpinnerEditText extends LinearLayout {
    String left_lb;//左侧文字显示
    String center_lb;//中间文字显示
    TextView left_txt;
    TextView center_txt;

    public SpinnerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpinnerEditText, defStyleAttr, 0);
        left_lb = a.getString(R.styleable.SpinnerEditText_left_lb);
        center_lb = a.getString(R.styleable.SpinnerEditText_center_lb);
        init(context);
        initViews();
        setLeftTxt(left_lb);
        setCenterTxt(center_lb);
    }

    public void setLeftTxt(String left) {
        if (left != null) {
            left_txt.setText(left);
        }
    }

    public void setCenterTxt(String center) {
        if (center != null) {
            center_txt.setText(center);
        }
    }


    public String getLeftText() {
        return left_txt.getText().toString().trim();
    }

    public String getCenterText(){
        return center_txt.getText().toString().trim();
    }
    private void initViews() {
        left_txt = (TextView) findViewById(R.id.spinner_left_tb);
        center_txt = (TextView) findViewById(R.id.spinner_center_tb);
    }

    public SpinnerEditText(Context context) {
        this(context, null);
    }

    public SpinnerEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /*-加载组件-*/
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.spinner_choice_text, this, true);//自定义控件绑定
    }
}
