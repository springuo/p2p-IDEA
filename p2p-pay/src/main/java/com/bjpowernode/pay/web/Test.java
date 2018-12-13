package com.bjpowernode.pay.web;

import java.math.BigDecimal;

/**
 * ClassName:Test
 * Package:com.bjpowernode.pay.web
 * Description:<br/>
 *
 * @date:2018/12/13 21:57
 * @author:郭鑫
 * @email:41700175@qq.com
 */
public class Test {
    public static void main(String[] args) {
        Double rechargeMoney = 0.1;
        BigDecimal bigDecimal = new BigDecimal(rechargeMoney);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal(100));
        int i = multiply.intValue();
        System.out.println(String.valueOf(i));
    }
}
