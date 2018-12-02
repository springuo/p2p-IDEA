package com.bjpowernode.p2p.service.loan;

/**
 * ClassName:OnlyNumberService
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/26 10:57
 * @author:guoxin@bjpowernode.com
 */
public interface OnlyNumberService {

    /**
     * 获取redis的全局唯一数字
     * @return
     */
    Long getOnlyNumber();
}
