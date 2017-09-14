package web.dto;

import java.time.LocalDateTime;

public class LocalFolderDTO {
    
    private Long id;
    private LocalDateTime ldtModified;
    private String localName;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getLdtModified() {
        return ldtModified;
    }
    public void setLdtModified(LocalDateTime ldtModified) {
        this.ldtModified = ldtModified;
    }
    public String getLocalName() {
        return localName;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    
}
