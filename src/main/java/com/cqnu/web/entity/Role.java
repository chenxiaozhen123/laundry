package com.cqnu.web.entity;

import javax.persistence.*;

@Table(name = "role")
public class Role {
    /**
     * 角色id
     */
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 权限等级
     */
    @Column(name = "role_priority")
    private Integer rolePriority;

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名
     *
     * @return role_name - 角色名
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名
     *
     * @param roleName 角色名
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取权限等级
     *
     * @return role_priority - 权限等级
     */
    public Integer getRolePriority() {
        return rolePriority;
    }

    /**
     * 设置权限等级
     *
     * @param rolePriority 权限等级
     */
    public void setRolePriority(Integer rolePriority) {
        this.rolePriority = rolePriority;
    }
}