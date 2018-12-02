package com.bjpowernode.pay.config;

/**
 * ClassName:PayConfig
 * Package:com.bjpowernode.pay.config
 * Description:
 *
 * @date:2018/9/26 11:16
 * @author:guoxin@bjpowernode.com
 */
public class PayConfig {

    private String alipayGatewayUrl;
    private String alipayAppid;
    private String merchantPrivateKey;
    private String alipayFormat;
    private String alipayCharset;
    private String alipayPublicKey;
    private String alipaySignType;
    private String returnUrl;
    private String notifyUrl;

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAlipayGatewayUrl() {
        return alipayGatewayUrl;
    }

    public void setAlipayGatewayUrl(String alipayGatewayUrl) {
        this.alipayGatewayUrl = alipayGatewayUrl;
    }

    public String getAlipayAppid() {
        return alipayAppid;
    }

    public void setAlipayAppid(String alipayAppid) {
        this.alipayAppid = alipayAppid;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getAlipayFormat() {
        return alipayFormat;
    }

    public void setAlipayFormat(String alipayFormat) {
        this.alipayFormat = alipayFormat;
    }

    public String getAlipayCharset() {
        return alipayCharset;
    }

    public void setAlipayCharset(String alipayCharset) {
        this.alipayCharset = alipayCharset;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipaySignType() {
        return alipaySignType;
    }

    public void setAlipaySignType(String alipaySignType) {
        this.alipaySignType = alipaySignType;
    }
}
