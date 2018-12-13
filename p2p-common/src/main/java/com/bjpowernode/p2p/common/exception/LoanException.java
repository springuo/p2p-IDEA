package com.bjpowernode.p2p.common.exception;

/**
 * ClassName:LoanException
 * Package:com.bjpowernode.p2p.common.exception
 * Description:<br/>
 *
 * @date:2018/12/8 13:23
 * @author:郭鑫
 * @email:41700175@qq.com
 */
public class LoanException extends RuntimeException{

    public LoanException() {
        super();
    }

    public LoanException(String message) {
        super(message);
    }

    public LoanException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanException(Throwable cause) {
        super(cause);
    }

    protected LoanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
