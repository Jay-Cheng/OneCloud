package com.zhengzijie.onecloud.service.dto;

import java.time.LocalDateTime;

public class LocalFolderDTO extends Sortable {
    
    private Long id;
    private LocalDateTime ldtCreate;
    private Long userID;
    private Long parent;
    
    public LocalFolderDTO() {}
    
    public void setLdtModified(LocalDateTime ldtModified) {
        this.ldtModified = ldtModified;
    }
    
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "LocalFolderDTO [id=" + id + ", ldtCreate=" + ldtCreate + ", userID=" + userID + ", parent=" + parent
                + ", ldtModified=" + ldtModified + ", localName=" + localName + "]";
    }
    
}
