package com.bjpowernode.p2p.service.loan;

/**
 * ClassName:IncomeRecordService
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/25 9:28
 * @author:guoxin@bjpowernode.com
 */

public interface IncomeRecordService {

    /**
     * 生成收益计划
     */
    void generateIncomePlan();

    /**
     * 收益返还
     */
    void generateIncomeBack();
}
