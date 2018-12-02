package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * ClassName:OnlyNumberServiceImpl
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/26 10:58
 * @author:guoxin@bjpowernode.com
 */
@Service("onlyNumberServiceImpl")
public class OnlyNumberServiceImpl implements OnlyNumberService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Long getOnlyNumber() {
        return redisTemplate.opsForValue().increment(Constants.ONLY_NUMBER,1);
    }
}
