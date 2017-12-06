package com.zhengzijie.onecloud.service.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    
    private LocalDateTime ldtCreate;
    
    private LocalDateTime ldtModified;

    private String username;
    
    private String nickname;
    
    private String photoURL;
    
    private Long usedCapacity;
    
    public UserDTO() {}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", ldtCreate=" + ldtCreate + ", ldtModified=" + ldtModified + ", username="
                + username + ", nickname=" + nickname + ", photoURL=" + photoURL + ", usedCapacity=" + usedCapacity
                + "]";
    }
    
}
