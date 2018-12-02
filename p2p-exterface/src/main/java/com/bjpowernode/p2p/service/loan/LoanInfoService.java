package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName:LoanInfoService
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/17 11:27
 * @author:guoxin@bjpowernode.com
 */
public interface LoanInfoService {

    /**
     * 获取平台历史平均年化收益率
     * @return
     */
    Double queryHistoryAverageRate();

    /**
     * 根据产品类型获取产品信息列表
     * @param paramMap
     * @return
     */
    List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> paramMap);

    /**
     * 分页查询产品信息列表
     * @param paramMap
     * @return
     */
    PaginatinoVO<LoanInfo> queryLoanInfoByPage(Map<String, Object> paramMap);

    /**
     * 根据产品标识获取产品详情
     * @param id
     * @return
     */
    LoanInfo queryLoanInfoById(Integer id);
}
