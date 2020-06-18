package com.example.hshop.model;

public class Giohang {
    public int idsp,soluongsp;
    public String tensp;
    public  String hinhanhsp;
    public long giasp;

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public Giohang(int idsp, int soluongsp, String tensp, String hinhanhsp, long giasp) {
        this.idsp = idsp;
        this.soluongsp = soluongsp;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
        this.giasp = giasp;
    }
}
