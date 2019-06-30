package com.github.kongpf8848.rxhttp.bean;

public class DownloadInfo extends ProgressInfo {

    private String destDir;
    private String fileName;

    public DownloadInfo(String url, String dir) {
        super(url);
        this.destDir=dir;
        this.fileName=getUrl().substring(getUrl().lastIndexOf("/")+1);
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
