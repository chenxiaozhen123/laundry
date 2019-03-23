package com.cqnu.base.model;

import java.io.Serializable;

/**
 * @Description 响应结果封装
 * @Author xzchen
 * @Date 2019/3/23 16:52
 * @Version 1.0
 **/
/**
 * <p/>
 * 响应状态编码code：
 * 正常数据-0000
 * 未知异常-9XXX
 * 异常数据-XXXX
 */
public class BaseRes implements Serializable {
    private static final long serialVersionUID = -1910116263806311190L;

    public static final String SUCCESS_CODE = "0000";
    public static final String SUCCESS_DESC = "成功";
    public static final String FAILURE_CODE = "1000";
    public static final String FAILURE_DESC = "失败";
    public static final String EXCEPTION_CODE = "9000";
    public static final String EXCEPTION_DESC = "异常";

    public static BaseRes getSuccess() {
        return getSuccess(null, 0);
    }

    public static BaseRes getSuccess(Object data) {
        return getSuccess(data, 0);
    }

    public static BaseRes getSuccess(Object data, long cost) {
        return new BaseRes(SUCCESS_CODE, SUCCESS_DESC, data, cost);
    }

    public static BaseRes getFailure(String desc) {
        return new BaseRes(FAILURE_CODE, desc == null ? FAILURE_DESC : desc, null, 0);
    }

    public static BaseRes getFailure(String desc, long cost) {
        return new BaseRes(FAILURE_CODE, desc == null ? FAILURE_DESC : desc, null, cost);
    }

    public static BaseRes getException(String desc) {
        return new BaseRes(EXCEPTION_CODE, desc == null ? EXCEPTION_DESC : desc, null, 0);
    }

    public static BaseRes getException(String desc, long cost) {
        return new BaseRes(EXCEPTION_CODE, desc == null ? EXCEPTION_DESC : desc, null, cost);
    }

    // 响应状态编码
    private String code;
    // 响应状态描述
    private String desc;
    // 响应数据
    private Object data;
    // 响应请求耗费的时间
    private long cost;

    public BaseRes() {
        this.code = SUCCESS_CODE;
        this.desc = SUCCESS_DESC;
    }

    private BaseRes(String code, String desc, Object data, long cost) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }
}
