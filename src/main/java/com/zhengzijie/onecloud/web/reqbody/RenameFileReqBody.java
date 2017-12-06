package com.zhengzijie.onecloud.web.reqbody;

import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

public class RenameFileReqBody {
    
    @NonNull @Size(min=1, max=100)
    private String localName;
    
    @NonNull @Size(min=1, max=100)
    private String localType;
    
    public RenameFileReqBody() {}
    
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

    @Override
    public String toString() {
        return "RenameFileReqBody [localName=" + localName + ", localType=" + localType + "]";
    }
    
}
