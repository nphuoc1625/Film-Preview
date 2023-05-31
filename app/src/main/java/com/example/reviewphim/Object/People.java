package com.example.reviewphim.Object;

import android.graphics.Bitmap;

public class People {
    int IDPeople,GioiTinh;
    String TenAnh;

    public Bitmap getAnh() {
        return Anh;
    }

    public void setAnh(Bitmap anh) {
        Anh = anh;
    }

    Bitmap Anh;
    String Ten;
    String TieuSu;
    String NgaySinh;
    String NoiSinh;

    public int getACorDC() {
        return ACorDC;
    }

    public void setACorDC(int ACorDC) {
        this.ACorDC = ACorDC;
    }

    int ACorDC ;
    public String getTenVaiDien() {
        return TenVaiDien;
    }

    public void setTenVaiDien(String tenVaiDien) {
        TenVaiDien = tenVaiDien;
    }

    String TenVaiDien;

    public People() {
    }

    public int getIDPeople() {
        return IDPeople;
    }

    public void setIDPeople(int IDPeople) {
        this.IDPeople = IDPeople;
    }

    public int getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getTenAnh() {
        return TenAnh;
    }

    public void setTenAnh(String tenAnh) {
        TenAnh = tenAnh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getTieuSu() {
        return TieuSu;
    }

    public void setTieuSu(String tieuSu) {
        TieuSu = tieuSu;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getNoiSinh() {
        return NoiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        NoiSinh = noiSinh;
    }
}
