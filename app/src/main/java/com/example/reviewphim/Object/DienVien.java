package com.example.reviewphim.Object;

import java.util.ArrayList;

public class DienVien {

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String tenDV) {
        TenDV = tenDV;
    }

    public String getGioiThieuDV() {
        return GioiThieuDV;
    }

    public void setGioiThieuDV(String gioiThieuDV) {
        GioiThieuDV = gioiThieuDV;
    }

    public String getGioiTinhDV() {
        return GioiTinhDV;
    }

    public void setGioiTinhDV(String gioiTinhDV) {
        GioiTinhDV = gioiTinhDV;
    }

    public String getNgaySinhDV() {
        return NgaySinhDV;
    }

    public void setNgaySinhDV(String ngaySinhDV) {
        NgaySinhDV = ngaySinhDV;
    }

    public String getNoiSinhDV() {
        return NoiSinhDV;
    }

    public void setNoiSinhDV(String noiSinhDV) {
        NoiSinhDV = noiSinhDV;
    }

    String TenDV;
    String GioiThieuDV;
    String GioiTinhDV;
    String NgaySinhDV;
    String NoiSinhDV;
    int IDDV;
    int AnhDV;

    public DienVien(String tenDV, String gioiThieuDV, String gioiTinhDV, String ngaySinhDV, String noiSinhDV, int IDDV, int anhDV) {
        TenDV = tenDV;
        GioiThieuDV = gioiThieuDV;
        GioiTinhDV = gioiTinhDV;
        NgaySinhDV = ngaySinhDV;
        NoiSinhDV = noiSinhDV;
        this.IDDV = IDDV;
        AnhDV = anhDV;
    }

    public DienVien()   {

    }


    public int getIDDV() {
        return IDDV;
    }

    public void setIDDV(int IDDV) {
        this.IDDV = IDDV;
    }

    public int getAnhDV() {
        return AnhDV;
    }

    public void setAnhDV(int anhDV) {
        AnhDV = anhDV;
    }


}
