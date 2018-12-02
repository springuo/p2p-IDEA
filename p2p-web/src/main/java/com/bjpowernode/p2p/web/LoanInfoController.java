package com.bjpowernode.p2p.web;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.BidUserTop;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import com.bjpowernode.p2p.service.user.FinanceAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:LoanInfoController
 * Package:com.bjpowernode.p2p.web
 * Description:
 *
 * @date:2018/9/18 11:57
 * @author:guoxin@bjpowernode.com
 */
@Controller
public class LoanInfoController {

    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private BidInfoService bidInfoService;

    @Autowired
    private FinanceAccountService financeAccountService;

    @RequestMapping(value = "/loan/loan")
    public String loan(HttpServletRequest request,Model model,
                       @RequestParam (value = "ptype",required = false) Integer ptype,
                       @RequestParam (value = "currentPage",required = false) Integer currentPage) {

        //判断当前页码是否为空，为空，默认值为第1页
        if (null == currentPage) {
            //默认为第1页
            currentPage = 1;
        }

        //准备分页查询参数
        Map<String,Object> paramMap = new HashMap<String,Object>();

        if (null != ptype) {
            //产品类型
            paramMap.put("productType",ptype);
        }

        int pageSize = 9;

        //起始下标
        paramMap.put("currentPage",(currentPage -1) * pageSize);

        //截取长度，每页显示条数
        paramMap.put("pageSize",pageSize);

        //分页查询产品信息列表(产品类型，页码，每页显示几条)  -> 返回分页模型对象（总记录条数、当前页要显示的数据）
        PaginatinoVO<LoanInfo> paginatinoVO = loanInfoService.queryLoanInfoByPage(paramMap);

        //计算总页数
        int totalPage = paginatinoVO.getTotal().intValue() / pageSize;

        //再次求余
        int mod = paginatinoVO.getTotal().intValue() % pageSize;
        if (mod > 0) {
            totalPage = totalPage + 1;
        }

        //总记录数
        model.addAttribute("totalRows",paginatinoVO.getTotal());
        //总页数
        model.addAttribute("totalPage",totalPage);
        //每页显示的数据
        model.addAttribute("loanInfoList",paginatinoVO.getDataList());
        //当前页码
        model.addAttribute("currentPage",currentPage);

        if (null != ptype) {
            //产品类型
            model.addAttribute("ptype",ptype);
        }


        //用户投资排行榜
        List<BidUserTop> bidUserTopList = bidInfoService.queryBidUserTop();
        model.addAttribute("bidUserTopList",bidUserTopList);


        return "loan";
    }


    @RequestMapping(value = "/loan/loanInfo")
    public String loanInfo(HttpServletRequest request,Model model,
                           @RequestParam (value = "id",required = true) Integer id) {

        //根据产品标识获取产品的详情
        LoanInfo loanInfo = loanInfoService.queryLoanInfoById(id);

        //根据产品标识获取该产品的所有投资记录
        List<BidInfo> bidInfoList = bidInfoService.queryBidInfoListByLoanId(id);

        model.addAttribute("loanInfo",loanInfo);
        model.addAttribute("bidInfoList",bidInfoList);

        //获取当前用户的信息
        User sessionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);

        //判断用户是否登录
        if (null != sessionUser) {

            //获取当前用户的帐户可用余额
            FinanceAccount financeAccount = financeAccountService.queryFinanceAccountByUid(sessionUser.getId());
            model.addAttribute("financeAccount",financeAccount);
        }




        return "loanInfo";
    }
}
