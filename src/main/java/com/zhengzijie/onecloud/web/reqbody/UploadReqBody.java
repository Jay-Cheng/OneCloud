package com.zhengzijie.onecloud.web.reqbody;

import javax.validation.constraints.NotNull;

public class UploadReqBody {
    
    @NotNull
    private long userID;
    @NotNull
    private String localName;
    @NotNull
    private String localType;
    @NotNull
    private long parent;
    @NotNull
    private String md5;
    
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
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
    public long getParent() {
        return parent;
    }
    public void setParent(long parent) {
        this.parent = parent;
    }
    public String getMd5() {
        return md5;
    }
    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    @Override
    public String toString() {
        return "UploadReqBody [userID=" + userID + ", localName=" + localName + ", localType=" + localType + ", parent="
                + parent + ", md5=" + md5 + "]";
    }
    
}
