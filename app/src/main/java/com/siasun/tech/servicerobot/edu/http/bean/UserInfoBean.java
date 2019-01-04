package com.siasun.tech.servicerobot.edu.http.bean;

/**
 * Created by jian.shi on 2019/1/4.
 *
 * @Email shijian1@siasun.com
 */
public class UserInfoBean {

    /**
     * id : 6eb8941eb1304c3bb778037997cbd5f2
     * phone : 15888888888
     * mac : asdfghj
     * isEnable : 1
     * isDelete : 0
     * updateTime : 2019-01-02 10:55:45
     * createTime : 2019-01-02 10:55:45
     */

    private String id;
    private String phone;
    private String mac;
    private int isEnable;
    private int isDelete;
    private String updateTime;
    private String createTime;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getPhone() { return phone;}

    public void setPhone(String phone) { this.phone = phone;}

    public String getMac() { return mac;}

    public void setMac(String mac) { this.mac = mac;}

    public int getIsEnable() { return isEnable;}

    public void setIsEnable(int isEnable) { this.isEnable = isEnable;}

    public int getIsDelete() { return isDelete;}

    public void setIsDelete(int isDelete) { this.isDelete = isDelete;}

    public String getUpdateTime() { return updateTime;}

    public void setUpdateTime(String updateTime) { this.updateTime = updateTime;}

    public String getCreateTime() { return createTime;}

    public void setCreateTime(String createTime) { this.createTime = createTime;}
}
