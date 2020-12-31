package com.example.imitation_wechat.Bean;

public class ChatBean {

    private String info;
    private int image;
    private String time;
    private boolean pd;

    public ChatBean(String info, int image, String time, boolean pd){
        this.info = info;
        this.image = image;
        this.time = time;
        this.pd=pd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPd() {
        return pd;
    }

    public void setPd(boolean pd) {
        this.pd = pd;
    }

}
