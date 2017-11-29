package com.zhengzijie.onecloud.service.dto;

import java.time.LocalDateTime;

public class LocalFileDTO extends Sortable {
    
    private Long id;
    private LocalDateTime ldtCreate;
    private Long userID;
    private Long fileID;
    private String localType;
    private Long parent;
    private Long size;
    private String url;

    public LocalFileDTO() {}

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

    public Long getFileID() {
        return fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public String getLocalType() {
        return localType;
    }

    public void setLocalType(String localType) {
        this.localType = localType;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LocalFileDTO [id=" + id + ", ldtCreate=" + ldtCreate + ", userID=" + userID + ", fileID=" + fileID
                + ", localType=" + localType + ", parent=" + parent + ", size=" + size + ", url=" + url
                + ", ldtModified=" + ldtModified + ", localName=" + localName + "]";
    }
    
}
