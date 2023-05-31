package com.example.reviewphim.Object;

import java.util.ArrayList;

public class DaoDien {
    String TenDD;
    String GioiThieuDD;
    String GioiTinhDD;
    String NgaySinhDD;
    String NoiSinhDD;
    int IDDaoDien;
    int AnhDD;

    public DaoDien(String tenDD, String gioiThieuDD, String gioiTinhDD, String ngaySinhDD, String noiSinhDD, int IDDaoDien, int anhDD) {
        TenDD = tenDD;
        GioiThieuDD = gioiThieuDD;
        GioiTinhDD = gioiTinhDD;
        NgaySinhDD = ngaySinhDD;
        NoiSinhDD = noiSinhDD;
        this.IDDaoDien = IDDaoDien;
        AnhDD = anhDD;
    }

    public String getTenDD() {
        return TenDD;
    }

    public void setTenDD(String tenDD) {
        TenDD = tenDD;
    }

    public String getGioiThieuDD() {
        return GioiThieuDD;
    }

    public void setGioiThieuDD(String gioiThieuDD) {
        GioiThieuDD = gioiThieuDD;
    }

    public String getGioiTinhDD() {
        return GioiTinhDD;
    }

    public void setGioiTinhDD(String gioiTinhDD) {
        GioiTinhDD = gioiTinhDD;
    }

    public String getNgaySinhDD() {
        return NgaySinhDD;
    }

    public void setNgaySinhDD(String ngaySinhDD) {
        NgaySinhDD = ngaySinhDD;
    }

    public String getNoiSinhDD() {
        return NoiSinhDD;
    }

    public void setNoiSinhDD(String noiSinhDD) {
        NoiSinhDD = noiSinhDD;
    }

    public int getIDDaoDien() {
        return IDDaoDien;
    }

    public void setIDDaoDien(int IDDaoDien) {
        this.IDDaoDien = IDDaoDien;
    }

    public DaoDien() {
    }

    public int getAnhDD() {
        return AnhDD;
    }

    public void setAnhDD(int anhDD) {
        AnhDD = anhDD;
    }

}
