package com.zhengzijie.onecloud.web.reqbody;

import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

public class RenameFolderReqBody {
    
    @NonNull @Size(min=1, max=100)
    private String localName;
    
    public RenameFolderReqBody() {}
    
    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String toString() {
        return "RenameFolderReqBody [localName=" + localName + "]";
    }
    
}
