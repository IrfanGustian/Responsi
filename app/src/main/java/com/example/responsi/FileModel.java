package com.example.responsi;

public class FileModel {
    private String Title;
    private String Download_link;

    public FileModel() {
    }

    public FileModel(String Title, String Download_link) {
        this.Title = Title;
        this.Download_link = Download_link;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDownload_link() {
        return Download_link;
    }

    public void setDownload_link(String Download_link) {
        this.Download_link = Download_link;
    }
}
