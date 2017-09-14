package dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "local_file", uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "parent", "local_name", "local_type" })})
@DynamicUpdate(true)
public class LocalFileDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(name = "gmt_create", nullable = false)
    private LocalDateTime ldtCreate;
    
    @Column(name = "gmt_modified", nullable = false)
    private LocalDateTime ldtModified;
    
    @Column(name = "user_id", nullable = false)
    private Long userID;
    
    @Column(name = "file_id", nullable = false)
    private Long fileID;
    
    @Column(name = "local_name", nullable = false)
    private String localName;
    
    @Column(name = "local_type", nullable = false)
    private String localType;
    
    @Column(name = "parent", nullable = false)
    private Long parent;
    
    public LocalFileDO() {}

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

    public Long getFileID() {
        return fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public String getLocalName() {
        return localName;
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
    
}
