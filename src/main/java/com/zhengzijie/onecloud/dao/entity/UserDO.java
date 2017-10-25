package com.zhengzijie.onecloud.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user")
@DynamicUpdate(true)
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(name = "ldt_create", nullable = false)
    private LocalDateTime ldtCreate;
    
    @Column(name = "ldt_modified", nullable = false)
    private LocalDateTime ldtModified;

    @Column(name = "account", unique = true, nullable = false)
    private String account;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "nickname", nullable = false)
    private String nickname;
    
    @Column(name = "photo_url", unique = true, nullable = false)
    private String photoURL;
    
    @Column(name = "used_capacity", nullable = false)
    private Long usedCapacity;
    
    @Column(name = "safe_password")
    private String safePassword;
    
    public UserDO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLdtCreate() {
        return ldtCreate;
    }

    public void setLdtCreate(LocalDateTime ldtCreate) {
        this.ldtCreate = ldtCreate;
    }

    public LocalDateTime getLdtModified() {
        return ldtModified;
    }

    public void setLdtModified(LocalDateTime ldtModified) {
        this.ldtModified = ldtModified;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Long getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Long usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public String getSafePassword() {
        return safePassword;
    }

    public void setSafePassword(String safePassword) {
        this.safePassword = safePassword;
    }
    
}
