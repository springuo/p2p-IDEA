package com.bjpowernode.p2p.web;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:BidInfoController
 * Package:com.bjpowernode.p2p.web
 * Description:
 *
 * @date:2018/9/21 14:30
 * @author:guoxin@bjpowernode.com
 */
@Controller
public class BidInfoController {

    @Autowired
    private BidInfoService bidInfoService;

    @RequestMapping(value = "/loan/invest")
    public @ResponseBody Object invest(HttpServletRequest request,
                                       @RequestParam (value = "loanId",required = true) Integer loanId,
                                       @RequestParam (value = "bidMoney",required = true) Double bidMoney) {
        Map<String,Object> retMap = new HashMap<String,Object>();

        //从session中获取用户的信息
        User sesionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);

        //准备请求参数
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //用户标识
        paramMap.put("uid",sesionUser.getId());
        //产品标识
        paramMap.put("loanId",loanId);
        //投资金额
        paramMap.put("bidMoney",bidMoney);
        //手机号码
        paramMap.put("phone",sesionUser.getPhone());


        //用户投资（用户标识，产品标识，投资金额） -> 返回结果ResultObject
        ResultObject resultObject = bidInfoService.invest(paramMap);

        //判断是否成功
        if (StringUtils.equals(Constants.SUCCESS,resultObject.getErrorCode())) {
            retMap.put(Constants.ERROR_MESSAGE,Constants.OK);
        } else {
            retMap.put(Constants.ERROR_MESSAGE,"投资人数过多，请稍后重试...");
            return retMap;
        }


        return retMap;
    }
}
