package com.cqnu.web.model;

/**
 * @Description 返回前台登录用户的信息
 * @Author xzchen
 * @Date 2019/3/12 17:34
 * @Version 1.0
 **/
public class AdminLoginInfo {
    /**
     * 工号
     */
    private String adminNo;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String telNum;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 角色id
     */
    private int roleId;
    /**
     * 权限等级
     */
    private Integer rolePriority;
    private String roleName;
    /**
     * 所属门店
     */
    private int shopNo;
    /**
     * 所属门店
     */
    private Integer shopName;
    /**
     * 状态
     */
    private String state;

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Integer getRolePriority() {
        return rolePriority;
    }

    public void setRolePriority(Integer rolePriority) {
        this.rolePriority = rolePriority;
    }

    public int getShopNo() {
        return shopNo;
    }

    public void setShopNo(int shopNo) {
        this.shopNo = shopNo;
    }

    public Integer getShopName() {
        return shopName;
    }

    public void setShopName(Integer shopName) {
        this.shopName = shopName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
