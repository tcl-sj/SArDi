package com.siasun.tech.servicerobot.edu.http.bean;

/**
 * Created by jian.shi on 2019/1/3.
 *
 * @Email shijian1@siasun.com
 */
public class TokenBean {

    /**
     * robotId : 4a600c51d1c9472289b79ec17ff2657f
     * token : c5fc1303ecd840218bef41bb3cfe2eff
     */

    private String robotId;
    private String token;
    private String macAddress;

    public TokenBean() {
        robotId = "";
        token = "";
    }

    public TokenBean(String robotId, String token) {
        this.robotId = robotId;
        this.token = token;
    }

    public String getRobotId() { return robotId;}

    public void setRobotId(String robotId) { this.robotId = robotId;}

    public String getToken() { return token;}

    public void setToken(String token) { this.token = token;}

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "robotId='" + robotId + '\'' +
                ", token='" + token + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
