package com.github.kongpf8848.rxhttp.bean;

public class DownloadInfo extends ProgressInfo {

    private String destDir;
    private String fileName;

    public DownloadInfo(String url, String dir,String filename) {
        super(url);
        this.destDir=dir;
        this.fileName=filename;
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

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "total='" + getTotal() + '\'' +
                ", progress='" + getProgress() + '\'' +
                '}';
    }
}
