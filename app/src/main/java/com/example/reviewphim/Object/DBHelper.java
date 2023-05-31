package com.example.reviewphim.Object;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.reviewphim.R;

import java.util.ArrayList;

public class DBHelper {
    Context context;
    String name = "PreviewFilm.db";
    public DBHelper(Context context) {
        this.context = context;
    }

    private SQLiteDatabase openDB(){
        return context.openOrCreateDatabase(name,Context.MODE_PRIVATE,null);
    }
    private void closeDB(SQLiteDatabase db){
        db.close();
    }
    public void createTable() {
        SQLiteDatabase db = openDB();
        String sqlFilm = "CREATE TABLE IF NOT EXISTS FILM(" +
                "IDFilm         INTEGER NOT NULL PRIMARY KEY," + //0
                "DaXem          Integer default(0), " +         //1
                "LoaiFilm       Integer,"+                      //2
                "AnhFilm        TEXT," +                        //3
                "TenFilm        TEXT," +                        //4
                "GioiThieuFilm  TEXT," +                        //5
                "NgayChieuFilm  TEXT," +                        //6
                "ThoiLuongFilm  TEXT," +                        //7
                "MuaFilm        Integer," +                     //8
                "SoTapFilm      Integer," +                     //9
                "TheLoaiFilm    TEXT," +                        //10
                "DaoDienFilm    TEXT," +                        //11
                "DanhGiaFilm    TEXT)";                         //12

        String sqlPEOPLE = "CREATE TABLE IF NOT EXISTS PEOPLE (" +
                " IDPeople           INTEGER NOT NULL PRIMARY KEY," +
                " ACorDC             integer," +
                " AnhPeople          TEXT," +
                " TenPeople          TEXT," +
                " GioiThieuPeople    TEXT," +
                " GioiTinhPeople     TEXT," +
                " NgaySinhPeople     TEXT," +
                " NoiSinhPeople      TEXT)";
        String sqlACTORSxFILM ="Create Table if not exists ACTORSxFILM (" +
                "IDFilm      INTEGER NOT NULL ," +
                "IDPeople    INTEGER NOT NULL ," +
                "AnhActor    TEXT," +
                "TenActor    TEXT," +
                "TenFilm     TEXT," +
                "TenVaiDien  TEXT," +
                "NgayChieu   TEXT)";
        String sqlFILMofDIRECTOR = "Create table if not exists FILMofDIRECTOR(" +
                "IDPeople INTEGER," +
                "IDFilm INTEGER UNIQUE NOT NULL," +
                "TenFilm TEXT,"+
                "NgayChieu TEXT)";
        db.execSQL(sqlFilm);
        db.execSQL(sqlPEOPLE);
        db.execSQL(sqlACTORSxFILM);
        db.execSQL(sqlFILMofDIRECTOR);
        closeDB(db);
    }
    //PEOPLE
//           " IDPeople           INTEGER NOT NULL PRIMARY KEY," +
//            " ACorDC             integer," +
//            " AnhPeople          Blob," +
//            " TenPeople          TEXT," +
//            " GioiThieuPeople    TEXT," +
//            " GioiTinhPeople     TEXT," +
//            " NgaySinhPeople     TEXT," +
//            " NoiSinhPeople      TEXT)";
    public void AddPeople(People people,ArrayList<Film> films ){
        SQLiteDatabase db = openDB();
        Cursor cursor = db.rawQuery("select count(IDPeople) from PEOPLE where IDPeople="+people.getIDPeople(),null);
        if (cursor!=null){
            if (cursor.moveToFirst())
                if (cursor.getInt(0)==0){
                    ContentValues cv = new ContentValues();
                    cv.put("IDPeople",people.getIDPeople());
                    cv.put("ACorDC",people.getACorDC());
                    cv.put("AnhPeople",people.getTenAnh());
                    Bitmap bm = people.getAnh();
                    bm = Bitmap.createScaledBitmap(bm,300,400,true);
                    ImageUltilities.saveToInternalStorage(context, bm,people.getTenAnh());
                    cv.put("TenPeople",people.getTen());
                    cv.put("GioiThieuPeople",people.getTieuSu());
                    cv.put("GioiTinhPeople",people.getGioiTinh());
                    cv.put("NgaySinhpeople",people.getNgaySinh());
                    cv.put("NoiSinhPeople",people.getNoiSinh());
                    db.insert("PEOPLE",null,cv);
                }
                for(int i=0;i<films.size();++i){
                    ContentValues cv = new ContentValues();
                    cv.put("IDPeople",people.getIDPeople());
                    cv.put("IDFilm",films.get(i).getIDFilm());
                    cv.put("NgayChieu",films.get(i).getNgayChieu());
                    cv.put("TenFilm",films.get(i).getTenFilm());
                    db.insert("ACTORSxFILM",null,cv);
                }
        }
        closeDB(db);
    }
    public void DeletePeople(int IDPeople){
        SQLiteDatabase database = openDB();
        Cursor cursor = database.rawQuery("Select AnhPeople from PEOPLE where IDPeople ="+IDPeople,null);
        if (cursor!=null && cursor.moveToFirst()){
            ImageUltilities.deleteImageFromStorage(context,cursor.getString(0));
            database.rawQuery("delete from PEOPLE where IDPeople ="+IDPeople,null);
            database.rawQuery("delete from ACTORSxFILM where IDPeople="+IDPeople,null);
        }
        closeDB(database);
    }
    public People GetPeople(int IDPeople){
        SQLiteDatabase database = openDB();
        Cursor cursor = database.rawQuery("select * from PEOPLE where IDPeople="+IDPeople,null);
        People people = new People();
        if (cursor.moveToFirst()) {
            do {
                people.setIDPeople(cursor.getInt(0));
                people.setTenAnh(cursor.getString(2));
                Bitmap bm = ImageUltilities.loadImageFromStorage(context,people.getTenAnh());
                people.setAnh(bm);
                people.setTen(cursor.getString(3));
                people.setTieuSu(cursor.getString(4));
                people.setGioiTinh(cursor.getInt(5));
                people.setNgaySinh(cursor.getString(6));
                people.setNoiSinh(cursor.getString(7));
            }while (cursor.moveToNext());

        }
        closeDB(database);
        return people;

    }
    public ArrayList<Film> GetActorFilms(int IDPeople){
        SQLiteDatabase database = openDB();
        ArrayList<Film> films = new ArrayList<>();
        Cursor cursor = database.rawQuery("select IDFilm,TenFilm,NgayChieu from ACTORSxFILM where IDPeople ="+IDPeople ,null   );
        if (cursor.moveToFirst()){
            do {
                Film film = new Film();
                film.setIDFilm(cursor.getInt(0));
                film.setTenFilm(cursor.getString(1));
                film.setNgayChieu(cursor.getString(2));
                films.add(film);
            }while (cursor.moveToNext());
        }
        closeDB(database);
        return films;
    }
    public ArrayList<Film> GetDirectorFilms(int IDPeople){
        SQLiteDatabase database = openDB();
        ArrayList<Film> films = new ArrayList<>();
        Cursor cursor = database.rawQuery("select IDFilm,TenFilm,NgayChieu from FILMOfDIRECTOR where IDPeople ="+IDPeople ,null   );
        if (cursor.moveToFirst()){
            do {
                Film film = new Film();
                film.setIDFilm(cursor.getInt(0));
                film.setTenFilm(cursor.getString(1));
                film.setNgayChieu(cursor.getString(2));
                films.add(film);
            }while (cursor.moveToNext());
        }
        closeDB(database);
        return films;
    }
    public ArrayList<People> GetALLDirectors(){
        SQLiteDatabase database = openDB();
        ArrayList<People> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("select IDPeople,AnhPeople,TenPeople from PEOPLE where ACorDC = 0",null);
        if (cursor.moveToFirst()){
            do {
                People people = new People();
                people.setIDPeople(cursor.getInt(0));
                people.setTenAnh(cursor.getString(1));
                people.setAnh(ImageUltilities.loadImageFromStorage(context,people.getTenAnh()));
                people.setTen(cursor.getString(2));
                arrayList.add(people);
            }while (cursor.moveToNext());
        }
        closeDB(database);
        return arrayList;
    }
    public ArrayList<People> GetALLActors(){
        SQLiteDatabase database = openDB();
        ArrayList<People> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("select IDPeople,AnhPeople,TenPeople from PEOPLE where ACorDC = 1",null);
        if (cursor.moveToFirst()){
            do {
                People people = new People();
                people.setIDPeople(cursor.getInt(0));
                people.setTenAnh(cursor.getString(1));
                people.setAnh(ImageUltilities.loadImageFromStorage(context,people.getTenAnh()));
                people.setTen(cursor.getString(2));
                arrayList.add(people);
            }while (cursor.moveToNext());
        }
        closeDB(database);
        return arrayList;
    }
    //Phim
//          "IDFilm         INTEGER NOT NULL PRIMARY KEY," + //0
//         "DaXem          Integer default(0), " +         //1
//         "LoaiFilm       Integer,"+                      //2
//         "AnhFilm        Blob," +                        //3
//         "TenFilm        TEXT," +                        //4
//         "GioiThieuFilm  TEXT," +                        //5
//         "NgayChieuFilm  TEXT," +                        //6
//         "ThoiLuongFilm  TEXT," +                        //7
//         "MuaFilm        Integer," +                     //8
//         "SoTapFilm      Integer," +                     //9
//         "TheLoaiFilm    TEXT," +                        //10
//         "DaoDienFilm    TEXT )";                        //11
//          DanhGiaFilm    TEXT)";                         //12
    public  ArrayList<Film> GetALLMovie(){
        SQLiteDatabase database = openDB();
        ArrayList<Film> films = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from FILM where LoaiFilm = 0 ",null);
        if (cursor!=null)
            if (cursor.moveToFirst()){
                do {
                    Film film = new Film();
                    film.setIDFilm(cursor.getInt(0));
                    film.setDaXem(cursor.getInt(1));
                    film.setLoaiFilm(cursor.getInt(2));
                    film.setTenAnhFilm(cursor.getString(3));
                    film.setAnhFilm(ImageUltilities.loadImageFromStorage(context,film.getTenAnhFilm()));
                    film.setTenFilm(cursor.getString(4));
                    film.setGioiThieuFilm(cursor.getString(5));
                    film.setNgayChieu(cursor.getString(6));
                    film.setThoiLuongFilm(cursor.getString(7));
                    film.setMuaFilm(cursor.getInt(8));
                    film.setSoTapFilm(cursor.getInt(9));
                    film.setTheLoaiFilm(cursor.getString(10));
                    film.setDaoDienFilm(cursor.getString(11));
                    film.setDanhGia(cursor.getString(12));
                    films.add(film);
                }while(cursor.moveToNext());

                closeDB(database);
                return films;
            }
        closeDB(database);
        return  null;
    }
    public  ArrayList<Film> GetALLTVShow(){
        SQLiteDatabase database = openDB();
        ArrayList<Film> films = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from FILM where LoaiFilm = 1 ",null);
        if (cursor!=null)
            if (cursor.moveToFirst()){
                do {
                    Film film = new Film();
                    film.setIDFilm(cursor.getInt(0));
                    film.setDaXem(cursor.getInt(1));
                    film.setLoaiFilm(cursor.getInt(2));
                    film.setTenAnhFilm(cursor.getString(3));
                    film.setAnhFilm(ImageUltilities.loadImageFromStorage(context,film.getTenAnhFilm()));
                    film.setTenFilm(cursor.getString(4));
                    film.setGioiThieuFilm(cursor.getString(5));
                    film.setNgayChieu(cursor.getString(6));
                    film.setThoiLuongFilm(cursor.getString(7));
                    film.setMuaFilm(cursor.getInt(8));
                    film.setSoTapFilm(cursor.getInt(9));
                    film.setTheLoaiFilm(cursor.getString(10));
                    film.setDaoDienFilm(cursor.getString(11));
                    film.setDanhGia(cursor.getString(12));
                    films.add(film);
                }while(cursor.moveToNext());

                closeDB(database);
                return films;
            }
        closeDB(database);
        return  null;
    }
    public ArrayList<People> GetFilmActors(int IDFilm){
        SQLiteDatabase database = openDB();
        ArrayList<People> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("select AnhActor,TenActor,TenVaiDien from ACTORSxFILM where IDFilm="+IDFilm,null);
        if (cursor.moveToFirst()){
            do {
                People people = new People();
                people.setAnh(ImageUltilities.loadImageFromStorage(context,cursor.getString(0)));
                people.setTen(cursor.getString(1));
                people.setTenVaiDien(cursor.getString(2));
                arrayList.add(people);
            }while (cursor.moveToNext());
        }
        closeDB(database);
        return arrayList;
    }
    public Film GetFilm(int IDFilm){
        SQLiteDatabase database = openDB();
        Cursor cursor = database.rawQuery("select * from FILM where IDFilm = "+IDFilm,null);
        Film film = new Film();
        if (cursor!=null)
            if (cursor.moveToFirst()){
                film.setIDFilm(cursor.getInt(0));
                film.setDaXem(cursor.getInt(1));
                film.setLoaiFilm(cursor.getInt(2));
                film.setTenAnhFilm(cursor.getString(3));
                film.setAnhFilm(ImageUltilities.loadImageFromStorage(context,film.getTenAnhFilm()));
                film.setTenFilm(cursor.getString(4));
                film.setGioiThieuFilm(cursor.getString(5));
                film.setNgayChieu(cursor.getString(6));
                film.setThoiLuongFilm(cursor.getString(7));
                film.setMuaFilm(cursor.getInt(8));
                film.setSoTapFilm(cursor.getInt(9));
                film.setTheLoaiFilm(cursor.getString(10));
                film.setDaoDienFilm(cursor.getString(11));
                closeDB(database);
                return film;
            }
        closeDB(database);
        return  null;
    }
    public boolean AddFilm(Film film,ArrayList<People> peoples){
        SQLiteDatabase db = openDB();
        Cursor cursor = db.rawQuery("Select count(IDFilm) from FILM where IDFilm = "+ film.getIDFilm(),null );
        //Add film
        if (cursor!=null)
            if (cursor.moveToFirst())
                if (cursor.getInt(0)==0){
                    ContentValues cv =  new ContentValues();
                    cv.put("IDFilm",film.getIDFilm());
                    cv.put("DaXem",film.getDaXem());
                    cv.put("LoaiFilm",film.getLoaiFilm());
                    ImageUltilities.saveToInternalStorage(context,film.getAnhFilm(),film.getTenAnhFilm());
                    cv.put("AnhFilm",film.getTenAnhFilm());
                    cv.put("TenFilm",film.getTenFilm());
                    cv.put("GioiThieuFilm",film.getGioiThieuFilm());
                    cv.put("NgayChieuFilm",film.getNgayChieu());
                    cv.put("ThoiLuongFilm",film.getThoiLuongFilm());
                    cv.put("MuaFilm",film.getMuaFilm());
                    cv.put("SoTapFilm",film.getThoiLuongFilm());
                    cv.put("TheLoaiFilm",film.getTheLoaiFilm());
                    cv.put("DaoDienFilm",film.getDaoDienFilm());
                    cv.put("DanhGiaFilm",film.getDanhGia());
                    db.insert("FILM",null,cv);
                }
        //Add actors
        for (int i = 0;i<peoples.size();++i){
            People people = peoples.get(i);
            int IDFilm = film.getIDFilm();
            int IDPeople = people.getIDPeople();
            Cursor cursor1 = db.rawQuery("select count(TenFilm) from ACTORSxFILM where IDFilm = "+IDFilm+" and IDPeople = "+IDPeople,null  );
            if (cursor1!=null)
                if (cursor1.moveToFirst())
                    if (cursor1.getInt(0)==0)
                     {
                            ContentValues cv = new ContentValues();
                            cv.put("IDFilm",IDFilm);
                            cv.put("IDPeople",IDPeople);
                            cv.put("AnhActor",people.getTenAnh());
                            ImageUltilities.saveToInternalStorage(context,people.getAnh(),people.getTenAnh());
                            cv.put("TenActor",people.getTen());
                            cv.put("TenFilm",film.getTenFilm());
                            cv.put("TenVaiDien",people.getTenVaiDien());
                            cv.put("NgayChieu",film.getNgayChieu());
                            db.insert("ACTORSxFILM",null,cv);
                     }
        }
        closeDB(db);
        return false;
    }
    public void DeleteFilm(Film film){
        SQLiteDatabase db = openDB();
        ImageUltilities.deleteImageFromStorage(context,film.getTenAnhFilm());
        Cursor cs = db.rawQuery("Select AnhActor from ACTORSxFILM where IDFilm ="+film.getIDFilm(),null);
        if (cs!=null)
            if (cs.moveToFirst())
                do{
                    ImageUltilities.deleteImageFromStorage(context,cs.getString(0));
                }while(cs.moveToNext());
        db.execSQL("delete from FILM where IDFilm = "+film.getIDFilm());
        db.execSQL("delete from ACTORSxFILM where IDFilm = "+film.getIDFilm());
        closeDB(db);
    }
    public void SetDaXem(int IDFilm, int values  ){
        SQLiteDatabase database = openDB();
        database.execSQL("update FILM set DaXem ="+values+" where IDFilm = "+IDFilm);
        closeDB(database);
    }
    public boolean CheckSavedPeople(int IDPeople){
        SQLiteDatabase db  = openDB();
        Cursor cursor = db.rawQuery("select count(IDPeople) from PEOPLE where IDPeople ="+IDPeople,null);
        if(cursor!=null)
            if (cursor.moveToFirst())
                if (cursor.getInt(0)==1){
                    closeDB(db);
                    return true;
                }
        closeDB(db);
        return false;
    }
    public boolean CheckSavedFilm(int IDFilm){
        SQLiteDatabase db  = openDB();
        Cursor cursor = db.rawQuery("select count(IDFilm) from FILM where IDFilm ="+IDFilm,null);
        if(cursor!=null)
            if (cursor.moveToFirst())
                if (cursor.getInt(0)==1){
                    closeDB(db);
                    return true;
                }
        closeDB(db);
        return false;
    }
    public boolean CheckDaXem(int IDFilm){
        SQLiteDatabase db  = openDB();
        Cursor cursor = db.rawQuery("select DaXem from FILM where IDFilm ="+IDFilm+" and DaXem = 1",null);
        if (cursor!=null)
            if (cursor.moveToFirst())
                if (cursor.getInt(0)==1){
                    closeDB(db);
                    return true;
                }
        closeDB(db);
        return false;
    }
}
