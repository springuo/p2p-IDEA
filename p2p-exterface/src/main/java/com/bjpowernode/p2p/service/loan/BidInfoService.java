package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.vo.BidUserTop;
import com.bjpowernode.p2p.model.vo.ResultObject;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BidInfoService
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/17 15:12
 * @author:guoxin@bjpowernode.com
 */
public interface BidInfoService {
    /**
     * 获取平台累计投资金额
     * @return
     */
    Double queryAllBidMoney();

    /**
     * 根据产品标识获取产品的所有投资记录（包含用户信息）
     * @param loanId
     * @return
     */
    List<BidInfo> queryBidInfoListByLoanId(Integer loanId);

    /**
     * 用户投资
     * @param paramMap
     * @return
     */
    ResultObject invest(Map<String, Object> paramMap);

    /**
     * 从redis缓存中获取用户投资排行榜
     * @return
     */
    List<BidUserTop> queryBidUserTop();
}
