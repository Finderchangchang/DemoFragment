package liuliu.demofragment.model;

import cn.bmob.v3.BmobObject;

/**
 * 个人信息Model
 * 作者：柳伟杰 on 2016/4/16 11:09
 * 邮箱：1031066280@qq.com
 */
public class UserModel extends BmobObject {
    private int id;//顺序编码
    private String u_id;//用户编码（1301604160001---130+年后两位+月日+0000）
    private String u_name;//用户名
    private String u_pwd;//密码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_pwd() {
        return u_pwd;
    }

    public void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }
}
