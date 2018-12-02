package com.bjpowernode.p2p.config;

/**
 * ClassName:Config
 * Package:com.bjpowernode.p2p.config
 * Description:
 *
 * @date:2018/9/20 15:14
 * @author:guoxin@bjpowernode.com
 */
public class Config {

    /**
     * 实名认证的key
     */
    private String realNameAppkey;
    /**
     * 实名认证的URL
     */
    private String realNameUrl;

    public String getRealNameAppkey() {
        return realNameAppkey;
    }

    public void setRealNameAppkey(String realNameAppkey) {
        this.realNameAppkey = realNameAppkey;
    }

    public String getRealNameUrl() {
        return realNameUrl;
    }

    public void setRealNameUrl(String realNameUrl) {
        this.realNameUrl = realNameUrl;
    }
}
