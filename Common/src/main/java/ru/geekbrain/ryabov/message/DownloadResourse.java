package ru.geekbrain.ryabov.message;

public class DownloadResourse extends Message{
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
