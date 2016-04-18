package liuliu.demofragment.ui.activity;

import liuliu.demofragment.R;
import liuliu.demofragment.base.BaseActivity;
import liuliu.demofragment.config.Key;

/**
 * 首页
 * 作者：柳伟杰 on 2016/4/8 15:00
 * 邮箱：1031066280@qq.com
 */
public class MainActivity extends BaseActivity {
    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initEvents() {
        String name = getIntent().getStringExtra(Key.LOGIN_USER_NAME);
    }
}
