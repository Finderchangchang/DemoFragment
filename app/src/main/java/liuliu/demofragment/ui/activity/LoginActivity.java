package liuliu.demofragment.ui.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.tauth.Tencent;

import net.tsz.afinal.annotation.view.CodeNote;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoaderFactory;
import liuliu.custom.control.edittext.ClearEditText;
import liuliu.demofragment.R;
import liuliu.demofragment.base.BaseActivity;
import liuliu.demofragment.base.BaseApplication;
import liuliu.demofragment.config.Config;
import liuliu.demofragment.iview.ILoginView;
import liuliu.demofragment.listener.LoginListener;
import liuliu.demofragment.model.UserModel;

/**
 * 登录页面
 * 作者：柳伟杰 on 2016/4/8 13:58
 * 邮箱：1031066280@qq.com
 */
public class LoginActivity extends BaseActivity implements ILoginView {
    @CodeNote(id = R.id.name_et)
    ClearEditText name_et;
    @CodeNote(id = R.id.password_et)
    ClearEditText password_et;
    @CodeNote(id = R.id.login_btn, click = "onClick")
    Button login_btn;
    @CodeNote(id = R.id.user_img_civ, click = "onClick")
    CubeImageView user_img_civ;
    @CodeNote(id = R.id.error_pwd_tv)
    TextView error_pwd_tv;
    LoginListener mLogin;
    @CodeNote(id = R.id.line_test_ll)
    LinearLayout line_test_ll;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initEvents() {
        Bmob.initialize(this, "e5518e6f6aa2968a45f121d661ad80ae");//初始化Bmob
        user_img_civ.loadImage(ImageLoaderFactory.create(this), "http://c.hiphotos.baidu.com/image/h%3D200/sign=fedf3cac90dda144c5096bb282b6d009/574e9258d109b3de6e8a7cd7cbbf6c81800a4c5c.jpg");
        mLogin = new LoginListener(this);
    }

    /**
     * 页面所有点击事件
     *
     * @param view
     * @author cc 2016年4月8日 15:34:11
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                queryData();
//                mLogin.loading(name_et.getText().toString(), password_et.getText().toString());
                Tencent mTencent = Tencent.createInstance(Config.QQ_APPID, this.getApplicationContext());

                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("qq", mTencent.getAccessToken(), mTencent.getExpiresIn() + "", "1104852415");
                BmobUser.loginWithAuthData(this, authInfo, new OtherLoginListener() {

                    @Override
                    public void onSuccess(JSONObject userAuth) {

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Log.i("smile", "第三方登陆失败：" + msg);
                    }

                });
                break;
            case R.id.user_img_civ:
                line_test_ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20));
//                UserModel user = new UserModel();
//                user.setU_id("1306020001");
//                user.setU_name("17093215800");
//                user.setU_pwd("123");
//                user.save(this, new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        Log.i("smile", "错误码：" + code + "，错误描述：" + msg);
//                    }
//                });
                break;
        }
    }

    /**
     * 查询数据
     */
    public void queryData() {
        BmobQuery query = new BmobQuery("_User");
        query.findObjects(this, new FindCallback() {

            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.i("smile", "查询成功:" + arg0.length());
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.i("smile", "查询失败:" + arg1);
            }
        });
    }

    /**
     * 访问后台返回的数据
     *
     * @param result
     * @author cc 2016年4月8日 15:34:59
     */
    @Override
    public void loadResult(boolean result) {
        if (result) {
            error_pwd_tv.setVisibility(View.GONE);
            BaseApplication.intentPost(MainActivity.class);
            BaseApplication.toastLong(getString(R.string.denglu_chenggong));
        } else {
            error_pwd_tv.setVisibility(View.VISIBLE);
        }
    }
}
