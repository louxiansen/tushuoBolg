package com.userlogmanager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hyrj on 2019/10/11.
 */
@Entity
@Table(name = "log_operation")
@GenericGenerator(name = "uuidH", strategy = "uuid")
public class LogOperation {

    private String id;
    private String operation;   //操作内容
    private String type;        //操作类型
    private String userId;      //用户id
    private String loginIP;     //登录ip
    private Date createTime;    //操作时间

    @Id
    @GeneratedValue(generator = "uuidH")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "login_ip")
    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
