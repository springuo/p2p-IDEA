package com.bjpowernode.p2p.model.vo;

import java.io.Serializable;

/**
 * ClassName:BidUserTop
 * Package:com.bjpowernode.p2p.model.vo
 * Description:
 *
 * @date:2018/9/25 12:05
 * @author:guoxin@bjpowernode.com
 */
public class BidUserTop implements Serializable {

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 分数：累计投资金额
     */
    private Double score;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
