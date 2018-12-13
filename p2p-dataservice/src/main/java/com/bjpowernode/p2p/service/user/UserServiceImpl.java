package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.common.exception.LoanException;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:UserServiceImpl
 * Package:com.bjpowernode.p2p.service.user
 * Description:
 *
 * @date:2018/9/17 14:52
 * @author:guoxin@bjpowernode.com
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Long queryAllUserCount() {
        //首先去redis缓存中查询，有：直接用

        //修改redis中key值的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //获取指定操作某一个key的操作对象
        BoundValueOperations<Object, Object> boundValueOps = redisTemplate.boundValueOps(Constants.ALL_USER_COUNT);

        //获取指定key的value值
        Long allUserCount = (Long) boundValueOps.get();

        //判断是否有值
        if (null == allUserCount) {

            //去数据库查询
            allUserCount = userMapper.selectAllUserCount();

            //将该值存放到redis缓存中
            boundValueOps.set(allUserCount, 15, TimeUnit.SECONDS);

        }


        return allUserCount;
    }


    @Override
    public User queryUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public void register(String phone, String loginPassword){

        //新增用户
        User user = new User();
        user.setPhone(phone);
        user.setLoginPassword(loginPassword);
        user.setAddTime(new Date());
        user.setLastLoginTime(new Date());


        try {
            userMapper.insertSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("新增理财用户失败.异常信息：" + e.getMessage());
            throw new LoanException("新增理财用户失败.异常信息：" + e.getMessage());
        }


        //新增帐户
        User userInfo = userMapper.selectUserByPhone(phone);
        FinanceAccount financeAccount = new FinanceAccount();
        financeAccount.setUid(userInfo.getId());
        financeAccount.setAvailableMoney(888.0);

        try {
            financeAccountMapper.insertSelective(financeAccount);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("新增理财用户失败.异常信息：" + e.getMessage());
            throw new LoanException("新增理财用户失败.异常信息：" + e.getMessage());
        }

    }


    @Override
    public int modifyUserById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public User login(String phone, String loginPassword) {

        //1.根据用户手机号和登录密码查询用户信息
        User user = userMapper.selectUserByPhoneAndLoginPassword(phone, loginPassword);

        //判断用户是否存在
        if (null != user) {

            //2.更新用户的登录时间
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setLastLoginTime(new Date());
            userMapper.updateByPrimaryKeySelective(updateUser);

        }

        return user;
    }
}
