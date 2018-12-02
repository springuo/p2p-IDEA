package com.bjpowernode.p2p.service.loan;

/**
 * ClassName:RedisService
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @Date 2018/11/30 14:05
 * @Author guoxin
 * @Email:41700175@qq.com
 */
public interface RedisService {

    /**
     * 将值存放到redis缓存中并设置失败时间
     * @param phone
     * @param randNumber
     * @param second 默认为秒
     * @return
     */
    void putKeyValue(String phone, String randNumber,Integer second);

    /**
     * 从redis中获取指定Key的值
     * @param phone
     * @return
     */
    String getValue(String phone);
}
