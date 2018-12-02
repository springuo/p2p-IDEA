package com.bjpowernode.p2p.model.vo;

import java.io.Serializable;

/**
 * ClassName:ResultObject
 * Package:com.bjpowernode.p2p.model.vo
 * Description:
 *
 * @date:2018/9/20 10:35
 * @author:guoxin@bjpowernode.com
 */
public class ResultObject implements Serializable {

    /**
     * 错误码
     */
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
