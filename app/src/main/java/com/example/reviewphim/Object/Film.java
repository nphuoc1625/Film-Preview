package com.example.reviewphim.Object;

import android.graphics.Bitmap;

public class Film {
//            IDFilm         INTEGER NOT NULL PRIMARY KEY," +
//            "DaXem            Integer default(0), "+
//            "AnhFilm        Blob," +
//            "TenFilm        TEXT," +
//            "GioiThieuFilm  TEXT," +
//            "NgayChieuFilm  TEXT," +
//            "ThoiLuongFilm  TEXT," +
//            "MuaFilm        Integer," +
//            "SoTapFilm      Integer," +
//            "TheLoaiFilm    TEXT," +
//            "DaoDienFilm    INTEGER )";
    private int IDFilm,DaXem,IDDaoDien,LoaiFilm,MuaFilm,SoTapFilm;
    private String TenAnhFilm;
    private String TenFilm, GioiThieuFilm,ThoiLuongFilm,DaoDienFilm, NgayChieu,TheLoaiFilm;
    private String DanhGia;

    public Bitmap getAnhFilm() {
        return AnhFilm;
    }

    public void setAnhFilm(Bitmap anhFilm) {
        AnhFilm = anhFilm;
    }

    private Bitmap AnhFilm;

    public Film() {
    }


    public String getTheLoaiFilm() {
        return TheLoaiFilm;
    }

    public void setTheLoaiFilm(String theLoaiFilm) {
        TheLoaiFilm = theLoaiFilm;
    }

    public int getIDFilm() {
        return IDFilm;
    }

    public void setIDFilm(int IDFilm) {
        this.IDFilm = IDFilm;
    }

    public int getDaXem() {
        return DaXem;
    }

    public void setDaXem(int daXem) {
        DaXem = daXem;
    }

    public int getIDDaoDien() {
        return IDDaoDien;
    }

    public void setIDDaoDien(int IDDaoDien) {
        this.IDDaoDien = IDDaoDien;
    }

    public int getLoaiFilm() {
        return LoaiFilm;
    }

    public void setLoaiFilm(int loaiFilm) {
        LoaiFilm = loaiFilm;
    }

    public String getTenAnhFilm() {
        return TenAnhFilm;
    }

    public void setTenAnhFilm(String tenAnhFilm) {
        TenAnhFilm = tenAnhFilm;
    }

    public String getTenFilm() {
        return TenFilm;
    }

    public void setTenFilm(String tenFilm) {
        TenFilm = tenFilm;
    }

    public String getGioiThieuFilm() {
        return GioiThieuFilm;
    }

    public void setGioiThieuFilm(String gioiThieuFilm) {
        this.GioiThieuFilm = gioiThieuFilm;
    }

    public String getThoiLuongFilm() {
        return ThoiLuongFilm;
    }

    public void setThoiLuongFilm(String thoiLuongFilm) {
        ThoiLuongFilm = thoiLuongFilm;
    }


    public String getDaoDienFilm() {
        return DaoDienFilm;
    }

    public void setDaoDienFilm(String daoDienFilm) {
        DaoDienFilm = daoDienFilm;
    }

    public String getNgayChieu() {
        return NgayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        NgayChieu = ngayChieu;
    }

    public void setMuaFilm(int muaFilm) {
        MuaFilm = muaFilm;
    }

    public int getMuaFilm() {
        return MuaFilm;
    }

    public int getSoTapFilm() {
        return SoTapFilm;
    }

    public void setSoTapFilm(int soTapFilm) {
        SoTapFilm = soTapFilm;
    }

    public String getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(String danhGia) {
        DanhGia = danhGia;
    }
}
