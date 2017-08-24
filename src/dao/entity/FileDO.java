package dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file")
public class FileDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(name = "gmt_create", nullable = false)
    private LocalDateTime gmtCreate;
    
    @Column(name = "gmt_modified", nullable = false)
    private LocalDateTime gmtModified;
    
    @Column(name = "md5", unique = true, nullable = false)
    private String md5;
    
    /* size是Int型，所以单个文件最大4GB */
    @Column(name = "size", nullable = false)
    private Integer size;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "url", unique = true, nullable = false)
    private String url;
    
    public FileDO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
