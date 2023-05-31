package com.example.reviewphim.Object;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUltilities {
    public static void saveToInternalStorage(Context context,Bitmap bitmapImage, String name){
        // Create imageDir
        File createfolder = context.getDir("images",Context.MODE_PRIVATE)   ;
        File mypath = new File(createfolder,name);
        try {
            OutputStream fos = new FileOutputStream(mypath);
            Bitmap.createScaledBitmap(bitmapImage,300,400,true);
            fos.write(BitmapConverter.getBytes(bitmapImage));
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Bitmap loadImageFromStorage(Context context,String name)
    {

        try {
            File path = context.getDir("images",Context.MODE_PRIVATE);
            File f= new File(path, name);
            InputStream fis = new FileInputStream(f);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b ;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void deleteImageFromStorage(Context context,String name){
        File file = context.getDir("images",Context.MODE_PRIVATE);
        File img = new File(file,name);
        img.delete();
    }
}
