package com.bjpowernode.p2p.service.loan;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:LoanInfoServiceImplTest
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @Date 2018/11/6 8:42
 * @Author guoxin
 * @Email:41700175@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LoanInfoServiceImplTest {

    @Autowired
    private LoanInfoService loanInfoService;

    @Test
    public void queryHistoryAverageRate() {

        Double historyAverageRate = loanInfoService.queryHistoryAverageRate();

        System.out.println("===========历史平均年化收益率：" + historyAverageRate);
    }
}