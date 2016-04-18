package liuliu.demofragment.listener;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import liuliu.demofragment.base.BaseApplication;
import liuliu.demofragment.iview.ILoginView;
import liuliu.demofragment.model.UserModel;

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

    /**
     * 登录接口
     *
     * @param name 用户名
     * @param pwd  密码
     */
    public void loading(String name, String pwd) {
        String bql = "select * from UserModel";//查询所有的游戏得分记录
        new BmobQuery<UserModel>().doSQLQuery(mContext, bql, new SQLQueryListener<UserModel>() {

            @Override
            public void done(BmobQueryResult<UserModel> result, BmobException e) {
                if (e == null) {
                    List<UserModel> list = (List<UserModel>) result.getResults();
                    if (list != null && list.size() > 0) {
                        mView.loadResult(true);
                    } else {
                        Log.i("smile", "查询成功，无数据返回");
                        mView.loadResult(false);
                    }
                } else {
                    mView.loadResult(false);
                    Log.i("smile", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });
    }
}
