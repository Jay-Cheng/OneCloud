package com.zhengzijie.onecloud.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "local_folder", uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "parent", "local_name" })})
public class LocalFolderDO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(name = "ldt_create", nullable = false)
    private LocalDateTime ldtCreate;
    
    @Column(name = "ldt_modified", nullable = false)
    private LocalDateTime ldtModified;
    
    @Column(name = "user_id", nullable = false)
    private Long userID;
    
    @Column(name = "local_name", nullable = false)
    private String localName;
    
    @Column(name = "parent", nullable = false)
    private Long parent;
    
    public LocalFolderDO() {}

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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "LocalFolderDO [id=" + id + ", ldtCreate=" + ldtCreate + ", ldtModified=" + ldtModified + ", userID="
                + userID + ", localName=" + localName + ", parent=" + parent + "]";
    }
    
}
