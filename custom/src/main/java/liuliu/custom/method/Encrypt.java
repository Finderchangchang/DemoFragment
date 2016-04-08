package liuliu.custom.method;

import java.security.MessageDigest;

/**
 * 加解密操作
 * 作者：柳伟杰 on 2016/3/4 10:20
 * 邮箱：1031066280@qq.com
 */
public class Encrypt {

    /**
     * 用SHA1对字符串进行加密
     *
     * @param key 需要加密的字符串
     * @return 加密以后的内容
     */
    public static String StringToSHA1(String key) {
        String s = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(key.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();
            for (int i = 0; i < digest.length; i++) {
                String sp = Integer.toHexString(digest[i]);
                if (sp.length() > 1) {
                    s += sp.substring(sp.length() - 2, sp.length());
                } else {
                    s += sp;
                }
            }
        } catch (Exception e) {

        }
        return s.toUpperCase();
    }
}
