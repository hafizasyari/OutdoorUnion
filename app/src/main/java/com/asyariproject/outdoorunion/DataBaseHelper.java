package com.asyariproject.outdoorunion;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    private static String DB_NAME = "penulisan.db";
    private static String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    public SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);
        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public List<String> getDeskripsitoko (String nama) { // Untuk Mengambil semua judul dalam note
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko where namatoko = '" + nama + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(1));// namatoko
                daftartoko.add(cursor.getString(2));// profile
                daftartoko.add(cursor.getString(3));// jambuka
                daftartoko.add(cursor.getString(4));// alamat
                daftartoko.add(cursor.getString(5));// website
                daftartoko.add(cursor.getString(6));// gambar
                daftartoko.add(cursor.getString(7));// longitude
                daftartoko.add(cursor.getString(8));// latitude
                daftartoko.add(cursor.getString(9));// notelp
            } while (cursor.moveToNext());
        }
        return daftartoko;
    }

    public String[] getAllid () { // Untuk Mengambil nama toko
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(0));// namatoko
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] getNamatoko () { // Untuk Mengambil nama toko
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(1));// namatoko
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] getGambartoko () { // Untuk Mengambil gambar
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(6));//gambar
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] getAlamatlist () { // Untuk Mengambil alamat list
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(10));// alamatlist
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] getDaerahlist () { // Untuk Mengambil daerah list
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(11));// daerahlist
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }


//untuk database search

    public String[] searchNamatoko (String nama) { // Untuk Mencari nama toko
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko where namatoko like '"+ nama + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(1));// namatoko
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] searchGambartoko (String nama) { // Untuk Mencari gambar
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko where namatoko like '"+ nama + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(6));//gambar
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] searchAlamatlist (String nama) { // Untuk Mencari alamat list
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko where namatoko like '"+ nama + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(10));// alamatlist
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }

    public String[] searchDaerahlist (String nama) { // Untuk Mencari daerah list
        ArrayList<String> daftartoko = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DaftarToko where namatoko like '"+ nama + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                daftartoko.add(cursor.getString(11));// daerahlist
            } while (cursor.moveToNext());
        }
        String[]daftar = new String [daftartoko.size()];
        daftartoko.toArray(daftar);
        return daftar;
    }
}