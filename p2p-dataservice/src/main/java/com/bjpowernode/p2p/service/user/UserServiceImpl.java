package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

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
            boundValueOps.set(allUserCount,15, TimeUnit.SECONDS);

        }


        return allUserCount;
    }


    @Override
    public User queryUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public ResultObject register(String phone, String loginPassword) {
        ResultObject resultObject = new ResultObject();
        resultObject.setErrorCode(Constants.SUCCESS);

        //新增用户
        User user = new User();
        user.setPhone(phone);
        user.setLoginPassword(loginPassword);
        user.setAddTime(new Date());
        user.setLastLoginTime(new Date());
        int insertUserCount = userMapper.insertSelective(user);

        if (insertUserCount > 0) {
            User userInfo = userMapper.selectUserByPhone(phone);
            //新增帐户
            FinanceAccount financeAccount = new FinanceAccount();
            financeAccount.setUid(userInfo.getId());
            financeAccount.setAvailableMoney(888.0);
            int insertFinanceCount = financeAccountMapper.insertSelective(financeAccount);

            if (insertFinanceCount < 0) {
                resultObject.setErrorCode(Constants.FAIL);
            }

        } else {
            resultObject.setErrorCode(Constants.FAIL);
        }




        return resultObject;
    }


    @Override
    public int modifyUserById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public User login(String phone, String loginPassword) {

        //1.根据用户手机号和登录密码查询用户信息
        User user = userMapper.selectUserByPhoneAndLoginPassword(phone,loginPassword);

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
