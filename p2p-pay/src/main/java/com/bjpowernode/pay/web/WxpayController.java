package com.bjpowernode.pay.web;

import com.bjpowernode.p2p.common.util.HttpClientUtils;
import com.bjpowernode.pay.config.PayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:WxpayController
 * Package:com.bjpowernode.pay
 * Description:
 *
 * @date:2018/9/27 11:08
 * @author:guoxin@bjpowernode.com
 */
@Controller
public class WxpayController {

    Logger logger = LogManager.getLogger(WxpayController.class);

    @Autowired
    private PayConfig payConfig;

    @RequestMapping(value = "/api/wxpay")
    public @ResponseBody Object wxpay(HttpServletRequest request,
                                      @RequestParam (value = "total_fee",required = true) String total_fee,
                                      @RequestParam (value = "out_trade_no",required = true) String out_trade_no,
                                      @RequestParam (value = "body",required = true) String body) throws Exception {


        //准备Map集合的请求参数
        Map<String,String> requestDataMap = new HashMap<String,String>();

        requestDataMap.put("appid",payConfig.getWxpayAppid());
        requestDataMap.put("mch_id",payConfig.getWxpayMchid());
        requestDataMap.put("nonce_str",WXPayUtil.generateNonceStr());
        requestDataMap.put("body",body);
        requestDataMap.put("out_trade_no",out_trade_no);

        BigDecimal bigDecimal = new BigDecimal(total_fee);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal(100));
        int intValue = multiply.intValue();

        String totalFee = String.valueOf(intValue);
        requestDataMap.put("total_fee",totalFee);
        requestDataMap.put("spbill_create_ip",payConfig.getPayHost());
        requestDataMap.put("notify_url",payConfig.getWxpayNotifyUrl());
        requestDataMap.put("trade_type",payConfig.getWxpayTradeType());
        requestDataMap.put("product_id",out_trade_no);

        //生成签名值
        String signature = WXPayUtil.generateSignature(requestDataMap, payConfig.getWxpayKey());
        requestDataMap.put("sign",signature);

        //将Map集成的请求参数转换为xml格式的请求参数
        String requestDataXml = WXPayUtil.mapToXml(requestDataMap);

        //调用微信统一下单API接口 -> 响应xml格式的字符串
        String responseDataXml = HttpClientUtils.doPostByXml(payConfig.getWxpayUrl(), requestDataXml);

        //将xml格式的字符串转换为Map集合
        Map<String, String> responseDataMap = WXPayUtil.xmlToMap(responseDataXml);

        return responseDataMap;
    }


    @RequestMapping(value = "/api/wxpayNotify")
    public void wxpayNotify(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        StringBuilder buffer = new StringBuilder();
        try{
            InputStream reqInput = request.getInputStream();
            if (reqInput == null) {
                //没有接收到数据!!!
                buffer.append("no-data");
            } else {
                BufferedReader bin = new BufferedReader(new InputStreamReader(reqInput, "utf-8"));
                String line = null;
                while ((line = bin.readLine()) != null) {
                    buffer.append(line).append("\r\n");
                }

                bin.close();
                reqInput.close();
            }



            String notifyXml = buffer.toString();
            logger.info("1111111微信异步通知消息内容：" + notifyXml);

            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("realName",notifyXml);


            HttpClientUtils.doPost(payConfig.getWxpayNotifyUrl(),paramMap);

            logger.info("22222222接收到微信异步通知消息之后");


        }catch(Exception e) {

            e.printStackTrace();

        }finally{
            logger.info("33333333333输出给微信的响应");
            //输出给微信的响应
            StringBuilder answerXml  = new StringBuilder();
            answerXml.append("<xml>")
                    .append("<return_code><![CDATA[SUCCESS]]></return_code>")
                    .append("<return_msg><![CDATA[OK]]></return_msg>")
                    .append("</xml>");

            PrintWriter pw = response.getWriter();
            pw.println(answerXml.toString());
            pw.flush();
            pw.close();
        }

    }
}
