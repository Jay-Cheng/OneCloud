package com.zhengzijie.onecloud.service.dto;

import java.time.LocalDateTime;

public class LocalFolderDTO extends Sortable {
    
    private Long id;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setLdtModified(LocalDateTime ldtModified) {
        this.ldtModified = ldtModified;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    
}
