package com.bjpowernode.p2p.web;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.common.util.DateUtils;
import com.bjpowernode.p2p.common.util.HttpClientUtils;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.loan.OnlyNumberService;
import com.bjpowernode.p2p.service.loan.RechargeRecordService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:RechargeRecordController
 * Package:com.bjpowernode.p2p.web
 * Description:
 *
 * @date:2018/9/26 10:50
 * @author:guoxin@bjpowernode.com
 */
@Controller
public class RechargeRecordController {

    @Autowired
    private RechargeRecordService rechargeRecordService;


    @Autowired
    private OnlyNumberService onlyNumberService;


    @RequestMapping(value = "/loan/toAlipayRecharge")
    public String toAlipayRecharge(HttpServletRequest request, Model model,
                                   @RequestParam (value = "rechargeMoney",required = true) Double rechargeMoney) {

        //从session中获取用户的信息
        User sessionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);

        //生成一个全局唯一充值订单号 = 时间戳 + redis全局唯一数字
        String rechargeNo = DateUtils.getTimeStamp() + onlyNumberService.getOnlyNumber();

        //生成充值记录（状态为：充值中）
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setUid(sessionUser.getId());
        rechargeRecord.setRechargeNo(rechargeNo);
        rechargeRecord.setRechargeMoney(rechargeMoney);
        rechargeRecord.setRechargeTime(new Date());
        rechargeRecord.setRechargeStatus("0");
        rechargeRecord.setRechargeDesc("支付宝充值");

        int addRechargeCount = rechargeRecordService.addRechargeRecord(rechargeRecord);

        if (addRechargeCount > 0) {

            //向pay工程的支付方法传递参数
            model.addAttribute("p2p_pay_alipay_url","http://localhost:9090/pay/api/alipay");
            model.addAttribute("rechargeNo",rechargeNo);
            model.addAttribute("rechargeMoney",rechargeMoney);
            model.addAttribute("subject","支付宝充值");

        } else {

            model.addAttribute("trade_msg","充值人数过多，请稍后重试...");
            return "toRechargeBack";
        }


        return "toAlipay";
    }

    @RequestMapping(value = "/loan/alipayBack")
    public String alipayBack(HttpServletRequest request,Model model,
                             @RequestParam (value = "signVerified",required = true) String signVerified,
                             @RequestParam (value = "out_trade_no",required = true) String out_trade_no,
                             @RequestParam (value = "total_amount",required = true) Double total_amount) {

        System.out.println("-----------p2p-web------alipayBack-----------");

        //判断验证签名是否成功
        if (StringUtils.equals(Constants.SUCCESS,signVerified)) {
            //成功

            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("out_trade_no",out_trade_no);

            //调用pay工程的订单查询接口 -> 返回订单的状态
            String jsonString = HttpClientUtils.doPost("http://localhost:9090/pay/api/alipayQuery", paramMap);

            //使用fastJson解析json格式的字符串
            //将json格式的字符串转换为JSON对象
            JSONObject jsonObject = JSONObject.parseObject(jsonString);

            //获取指定key的value值
            JSONObject tradeQueryResponse = jsonObject.getJSONObject("alipay_trade_query_response");

            //获取通信标识code
            String code = tradeQueryResponse.getString("code");

            //判断通信是否成功
            if (StringUtils.equals("10000",code)) {

                //通信成功:获取业务处理结果trade_status
                String tradeStatus = tradeQueryResponse.getString("trade_status");

                /*交易状态：
                WAIT_BUYER_PAY（交易创建，等待买家付款）
                TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
                TRADE_SUCCESS（交易支付成功）
                TRADE_FINISHED（交易结束，不可退款）*/

                if ("TRADE_CLOSED".equals(tradeStatus)) {
                    //更新充值记录的状态为2充值失败
                    RechargeRecord updateRechargeRecord = new RechargeRecord();
                    updateRechargeRecord.setRechargeNo(out_trade_no);
                    updateRechargeRecord.setRechargeStatus("2");
                    int modifyRechargeCount = rechargeRecordService.modifyRechargeRecordByRechargeNo(updateRechargeRecord);

                    if (modifyRechargeCount < 0) {
                        model.addAttribute("trade_msg","充值人数过多，请稍后重试...");
                        return "toRechargeBack";
                    }

                }

                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    User sessionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);
                    paramMap.put("uid",sessionUser.getId());
                    paramMap.put("rechargeNo",out_trade_no);
                    paramMap.put("rechargeMoney",total_amount);

                    //支付宝扣款成功：给用户充值【1.更新帐户可用余额 2.更新充值记录的状态为1充值成功】(用户标识，充值订单号，充值金额)
                    int rechargeCount = rechargeRecordService.recharge(paramMap);

                    if (rechargeCount < 0) {
                        model.addAttribute("trade_msg","充值人数过多，请稍后重试...");
                        return "toRechargeBack";
                    }
                }


            } else {

                //通信失败
                model.addAttribute("trade_msg","通信失败，请稍后重试...");
                return "toRechargeBack";
            }


        } else {
            //失败
            model.addAttribute("trade_msg","充值人数过多，请稍后重试...");
            return "toRechargeBack";
        }


        return "redirect:/loan/myCenter";
    }






    @RequestMapping (value = "/loan/toWxpayRecharge")
    public String toWxpayRecharge(HttpServletRequest request, Model model,
                                  @RequestParam (value = "rechargeMoney",required = true) Double rechargeMoney) {

        //从session中获取用户的信息
        User sessionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);

        //生成一个全局唯一充值订单号 = 时间戳 + redis全局唯一数字
        String rechargeNo = DateUtils.getTimeStamp() + onlyNumberService.getOnlyNumber();

        //生成充值记录（状态为：充值中）
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setUid(sessionUser.getId());
        rechargeRecord.setRechargeNo(rechargeNo);
        rechargeRecord.setRechargeMoney(rechargeMoney);
        rechargeRecord.setRechargeTime(new Date());
        rechargeRecord.setRechargeStatus("0");
        rechargeRecord.setRechargeDesc("微信充值");

        int addRechargeCount = rechargeRecordService.addRechargeRecord(rechargeRecord);

        if(addRechargeCount > 0) {

            //跳转至展示二维码页面
            model.addAttribute("rechargeNo",rechargeNo);
            model.addAttribute("rechargeMoney",rechargeMoney);
            model.addAttribute("rechargeTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        } else {
            model.addAttribute("trade_msg","充值人数过多，请稍后重试...");
            return "toRechargeBack";
        }


        return "generateQRCode";
    }


    @RequestMapping(value = "/loan/generateQRCode")
    public void generateQRCode(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam (value = "rechargeMoney",required = true) Double rechargeMoney,
                               @RequestParam (value = "rechargeNo",required = true) String rechargeNo) throws IOException, WriterException {

        Map<String,Object> paramMap = new HashMap<String,Object>();

        paramMap.put("out_trade_no",rechargeNo);
        paramMap.put("total_fee",rechargeMoney);
        paramMap.put("body","微信充值");


        //调用pay工程的统一下单接口 -> 返回参数，包含code_url
        String jsonString = HttpClientUtils.doPost("http://localhost:9090/pay/api/wxpay", paramMap);

        //使用fastJson解析json格式的字符串
        //将json格式的字符串转换为json对象
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        //获取通信标识return_code
        String returnCode = jsonObject.getString("return_code");

        //判断通信是否成功
        if (StringUtils.equals(Constants.SUCCESS,returnCode)) {

            //获取业务处理结果
            String resultCode = jsonObject.getString("result_code");

            //判断业务处理结果
            if (StringUtils.equals(Constants.SUCCESS,resultCode)) {

                //将code_url转换为二维码
                String codeUrl = jsonObject.getString("code_url");

                Map<EncodeHintType,Object> hintMap = new HashMap<EncodeHintType, Object>();
                hintMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                //创建一个矩阵对象
                BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,200,200,hintMap);

                OutputStream outputStream = response.getOutputStream();

                MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);

            } else {
                response.sendRedirect(request.getContextPath() + "/toRechargeBack.jsp");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/toRechargeBack.jsp");
        }

    }
}
