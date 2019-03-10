package com.cqnu.base.model;

/**
 * @Description 邮件模板参数对应的dto
 * @Author xzchen
 * @Date 2019/3/8 23:23
 * @Version 1.0
 **/
public class Message {
    private String username;
    private String gender;
    private String jobNumber;
    private String initalPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getInitalPassword() {
        return initalPassword;
    }

    public void setInitalPassword(String initalPassword) {
        this.initalPassword = initalPassword;
    }
}