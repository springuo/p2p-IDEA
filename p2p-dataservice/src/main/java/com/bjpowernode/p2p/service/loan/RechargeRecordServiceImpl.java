package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.mapper.loan.RechargeRecordMapper;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ClassName:RechargeRecordServiceImpl
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/26 11:03
 * @author:guoxin@bjpowernode.com
 */
@Service("rechargeRecordServiceImpl")
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public int addRechargeRecord(RechargeRecord rechargeRecord) {
        return rechargeRecordMapper.insertSelective(rechargeRecord);
    }

    @Override
    public int modifyRechargeRecordByRechargeNo(RechargeRecord rechargeRecord) {
        return rechargeRecordMapper.updateRechargeRecordByRechargeNo(rechargeRecord);
    }

    @Override
    public int recharge(Map<String, Object> paramMap) {

        //更新帐户可用余额
        int updateFinanceCount = financeAccountMapper.updateFinanceAccountByRecharge(paramMap);

        if (updateFinanceCount > 0) {
            //更新充值记录的状态
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setRechargeNo((String) paramMap.get("rechargeNo"));
            rechargeRecord.setRechargeStatus("1");
            int rechargeRecordByRechargeNo = rechargeRecordMapper.updateRechargeRecordByRechargeNo(rechargeRecord);

            if (rechargeRecordByRechargeNo < 0) {
                return 0;
            }

        } else {
            return 0;
        }




        return 1;
    }
}
