package com.zhengzijie.onecloud.web.reqbody;

import java.util.List;

public class ShredReqBody {
    
    private List<Long> files;
    private List<Long> folders;
    
    public List<Long> getFiles() {
        return files;
    }
    public void setFiles(List<Long> files) {
        this.files = files;
    }
    public List<Long> getFolders() {
        return folders;
    }
    public void setFolders(List<Long> folders) {
        this.folders = folders;
    }
    
    @Override
    public String toString() {
        return "ShredReqBody [files=" + files + ", folders=" + folders + "]";
    }
    
}
