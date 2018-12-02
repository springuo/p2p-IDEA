package com.bjpowernode.p2p.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:PaginatinoVO
 * Package:com.bjpowernode.p2p.model.vo
 * Description:
 *
 * @date:2018/9/18 12:10
 * @author:guoxin@bjpowernode.com
 */
public class PaginatinoVO<T> implements Serializable {

    /**
     * 总记录条数
     */
    private Long total;

    /**
     * 显示数据
     */
    private List<T> dataList;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
