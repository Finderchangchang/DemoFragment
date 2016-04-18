package liuliu.demofragment.ui.activity;

import cn.bmob.v3.Bmob;
import liuliu.custom.manage.Utils;
import liuliu.demofragment.R;
import liuliu.demofragment.base.BaseActivity;
import liuliu.demofragment.base.BaseApplication;

/**
 * 作者：柳伟杰 on 2016/4/16 11:19
 * 邮箱：1031066280@qq.com
 */
public class LoadingActivity extends BaseActivity {
    @Override
    public void initViews() {
        setContentView(R.layout.activity_loading);
        BaseApplication.intentPost(LoginActivity.class);
        this.finish();
    }

    @Override
    public void initEvents() {

    }
}
