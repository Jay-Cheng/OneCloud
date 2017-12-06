package com.zhengzijie.onecloud.web.reqbody;

import java.util.List;

public class MoveReqBody {
    
    private List<Long> files;
    private List<Long> folders;
    private Long dest;
    
    public MoveReqBody() {}
    
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
    public Long getDest() {
        return dest;
    }
    public void setDest(Long dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "MoveActionForm [files=" + files + ", folders=" + folders + ", dest=" + dest + "]";
    }
    
}
