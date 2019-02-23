package com.cqnu.base.common.exception;

/**
 * @Description 自定义异常
 * @Author xzchen
 * @Date 2019/2/23 14:00
 * @Version 1.0
 **/
public class LaundryException extends RuntimeException{

    private String msg;
    // 90000以上需要特殊处理: 90001-session过期
    private int code = 0;

    public LaundryException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LaundryException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public LaundryException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public LaundryException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static LaundryException getSessionTimeoutException(String msg) {
        return new LaundryException(msg, 90001);
    }
}

