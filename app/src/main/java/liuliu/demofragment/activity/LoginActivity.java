package liuliu.demofragment.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.tsz.afinal.annotation.view.CodeNote;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoaderFactory;
import liuliu.demofragment.R;
import liuliu.demofragment.base.BaseActivity;
import liuliu.demofragment.iview.ILoginView;

/**
 * 登录页面
 * 作者：柳伟杰 on 2016/4/8 13:58
 * 邮箱：1031066280@qq.com
 */
public class LoginActivity extends BaseActivity implements ILoginView {
    @CodeNote(id = R.id.name_et)
    EditText name_et;
    @CodeNote(id = R.id.password_et)
    EditText password_et;
    @CodeNote(id = R.id.login_btn, click = "click")
    Button login_btn;
    @CodeNote(id = R.id.user_img_civ)
    CubeImageView user_img_civ;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initEvents() {
        user_img_civ.loadImage(ImageLoaderFactory.create(this), "http://c.hiphotos.baidu.com/image/h%3D200/sign=fedf3cac90dda144c5096bb282b6d009/574e9258d109b3de6e8a7cd7cbbf6c81800a4c5c.jpg");
    }

    /**
     * 页面所有点击事件
     *
     * @param view
     * @author cc 2016年4月8日 15:34:11
     */
    public void click(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                break;
        }
    }

    /**
     * 访问后台返回的数据
     *
     * @param result
     * @author cc 2016年4月8日 15:34:59
     */
    @Override
    public void loadResult(boolean result) {

    }
}
