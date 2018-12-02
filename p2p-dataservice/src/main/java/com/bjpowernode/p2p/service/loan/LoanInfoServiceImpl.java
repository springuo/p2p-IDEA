package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:LoanInfoServiceImpl
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2018/9/17 12:01
 * @author:guoxin@bjpowernode.com
 */
@Service("loanInfoServiceImpl")
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public Double queryHistoryAverageRate() {
        //先去redis缓存中获取该，有:直接使用，没有：去数据库查询并存放到redis缓存中

        //修改key值的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //获取操作key=value的数据类型的redis的操作对象,并获取指定key的value值
        Double historyAverageRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);

        //判断是否有值
        if (null == historyAverageRate) {
            //没有值：去数据库查询
            historyAverageRate = loanInfoMapper.selectHistoryAverageRate();

            //将该值存放到redis缓存中
            redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE,historyAverageRate,15, TimeUnit.SECONDS);
        }

        return historyAverageRate;
    }

    @Override
    public List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> paramMap) {
        return loanInfoMapper.selectLoanInfoByPage(paramMap);
    }

    @Override
    public PaginatinoVO<LoanInfo> queryLoanInfoByPage(Map<String, Object> paramMap) {
        PaginatinoVO<LoanInfo> paginatinoVO = new PaginatinoVO<>();

        Long total = loanInfoMapper.selectTotal(paramMap);

        //查询总记录数
        paginatinoVO.setTotal(total);

        List<LoanInfo> loanInfoList = loanInfoMapper.selectLoanInfoByPage(paramMap);

        //查询显示数据
        paginatinoVO.setDataList(loanInfoList);


        return paginatinoVO;
    }


    @Override
    public LoanInfo queryLoanInfoById(Integer id) {
        return loanInfoMapper.selectByPrimaryKey(id);
    }


}
