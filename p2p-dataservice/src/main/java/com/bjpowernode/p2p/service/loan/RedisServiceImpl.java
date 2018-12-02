package com.bjpowernode.p2p.service.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:RedisServiceImpl
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/12/1 13:55
 * @author:guoxin
 */
@Service("redisServiceImpl")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void putKeyValue(String phone, String randNumber, Integer second) {
        redisTemplate.opsForValue().set(phone,randNumber,second, TimeUnit.SECONDS);
    }

    @Override
    public String getValue(String phone) {
        return (String) redisTemplate.opsForValue().get(phone);
    }
}
