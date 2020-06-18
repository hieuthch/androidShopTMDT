package com.example.hshop.model;

public class Loaisp {
    public int id;
    public String tenLoaiSp;
    private String imgLoaiSp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSp() {
        return tenLoaiSp;
    }

    public void setTenLoaiSp(String tenLoaiSp) {
        this.tenLoaiSp = tenLoaiSp;
    }

    public String getImgLoaiSp() {
        return imgLoaiSp;
    }

    public void setImgLoaiSp(String imgLoaiSp) {
        this.imgLoaiSp = imgLoaiSp;
    }

    public Loaisp(int id, String tenLoaiSp, String imgLoaiSp) {
        this.id = id;
        this.tenLoaiSp = tenLoaiSp;
        this.setImgLoaiSp(imgLoaiSp);
    }
}
