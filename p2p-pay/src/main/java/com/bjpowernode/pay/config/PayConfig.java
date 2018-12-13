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

    private String format;
    private String charset;
    private String payHost;


    private String alipayGatewayUrl;
    private String alipayAppid;
    private String alipayPrivateKey;
    private String alipayPublicKey;
    private String alipaySignType;
    private String alipayReturnUrl;
    private String alipayNotifyUrl;


    private String wxpayAppid;
    private String wxpayMchid;
    private String wxpayNotifyUrl;
    private String wxpayTradeType;
    private String wxpayKey;
    private String wxpayUrl;





    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
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

    public String getAlipayPrivateKey() {
        return alipayPrivateKey;
    }

    public void setAlipayPrivateKey(String alipayPrivateKey) {
        this.alipayPrivateKey = alipayPrivateKey;
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

    public String getAlipayReturnUrl() {
        return alipayReturnUrl;
    }

    public void setAlipayReturnUrl(String alipayReturnUrl) {
        this.alipayReturnUrl = alipayReturnUrl;
    }

    public String getAlipayNotifyUrl() {
        return alipayNotifyUrl;
    }

    public void setAlipayNotifyUrl(String alipayNotifyUrl) {
        this.alipayNotifyUrl = alipayNotifyUrl;
    }


    public String getPayHost() {
        return payHost;
    }

    public void setPayHost(String payHost) {
        this.payHost = payHost;
    }

    public String getWxpayAppid() {
        return wxpayAppid;
    }

    public void setWxpayAppid(String wxpayAppid) {
        this.wxpayAppid = wxpayAppid;
    }

    public String getWxpayMchid() {
        return wxpayMchid;
    }

    public void setWxpayMchid(String wxpayMchid) {
        this.wxpayMchid = wxpayMchid;
    }

    public String getWxpayNotifyUrl() {
        return wxpayNotifyUrl;
    }

    public void setWxpayNotifyUrl(String wxpayNotifyUrl) {
        this.wxpayNotifyUrl = wxpayNotifyUrl;
    }

    public String getWxpayTradeType() {
        return wxpayTradeType;
    }

    public void setWxpayTradeType(String wxpayTradeType) {

        this.wxpayTradeType = wxpayTradeType;
    }

    public String getWxpayKey() {
        return wxpayKey;
    }

    public void setWxpayKey(String wxpayKey) {
        this.wxpayKey = wxpayKey;
    }

    public String getWxpayUrl() {
        return wxpayUrl;
    }

    public void setWxpayUrl(String wxpayUrl) {
        this.wxpayUrl = wxpayUrl;
    }
}
