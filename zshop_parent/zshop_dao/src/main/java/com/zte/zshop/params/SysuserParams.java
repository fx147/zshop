package com.zte.zshop.params;

/**
 * Author:helloboy
 * Date:2022-04-29 8:25
 * Description:<描述>
 *     封装需要查询的字段
 */
public class SysuserParams {

    //注意：对应表单中元素的name的值，这样springmvc才能完成注入，原理类似于VO

    private String name;

    private String loginName;

    private String phone;

    private Integer roleId;

    private Integer isValid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
