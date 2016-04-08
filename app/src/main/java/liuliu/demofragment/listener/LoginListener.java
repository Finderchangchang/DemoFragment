package liuliu.demofragment.listener;

import android.content.Context;

import liuliu.demofragment.base.BaseApplication;
import liuliu.demofragment.iview.ILoginView;

/**
 * 作者：柳伟杰 on 2016/4/8 15:11
 * 邮箱：1031066280@qq.com
 */
public class LoginListener {
    private ILoginView mView;
    private Context mContext;

    public LoginListener(ILoginView mView) {
        this.mContext = BaseApplication.getContext();
        this.mView = mView;
    }
    public void loading() {
        mView.loadResult(true);
    }
}
