package com.zhengzijie.onecloud.service.dto;

import java.time.LocalDateTime;

public class LocalFileDTO extends Sortable {
    
    private Long id;
    private Long fileID;
    private String localType;
    private Integer size;
    private String url;

    public LocalFileDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLdtModified(LocalDateTime ldtModified) {
        this.ldtModified = ldtModified;
    }

    public Long getFileID() {
        return fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalType() {
        return localType;
    }

    public void setLocalType(String localType) {
        this.localType = localType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
