package com.example.imitation_wechat.Bean;

public class InfoBean {
    private int image;
    private String name;
    private String info;
    private String time;

    public InfoBean(int image,String name,String info,String time){
        this.image=image;
        this.name=name;
        this.info=info;
        this.time=time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
