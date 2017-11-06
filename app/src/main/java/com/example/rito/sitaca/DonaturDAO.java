package com.example.rito.sitaca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYU ARYSTYANA on 11/04/2015.
 */

//Here comes the CRUDs
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DonaturDAO {

    public class SummaryBuku{
        int individu;
        int organisasi;
        int beli_sendiri;
        int yayasan;
        public SummaryBuku(int individu, int organisasi, int beli_sendiri, int yayasan)
        {
            this.individu = individu;
            this.organisasi = organisasi;
            this.beli_sendiri = beli_sendiri;
            this.yayasan = yayasan;
        }

        public int getIndividu() {
            return individu;
        }

        public void setIndividu(int individu) {
            this.individu = individu;
        }

        public int getOrganisasi() {
            return organisasi;
        }

        public void setOrganisasi(int organisasi) {
            this.organisasi = organisasi;
        }

        public int getBeli_sendiri() {
            return beli_sendiri;
        }

        public void setBeli_sendiri(int beli_sendiri) {
            this.beli_sendiri = beli_sendiri;
        }

        public int getYayasan() {
            return yayasan;
        }

        public void setYayasan(int yayasan) {
            this.yayasan = yayasan;
        }
    }

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {Donatur.KEY_ID_donatur,
            Donatur.KEY_nama,
            Donatur.KEY_alamat,
            Donatur.KEY_jenisDonatur,
            Donatur.KEY_namaKontak,
            Donatur.KEY_noTelp};

    public DonaturDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("Donatur", "Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.execSQL("PRAGMA foreign_keys = ON;");
    }

    public void close(){
        mDbHelper.close();
    }

    public void createDonatur(String nama, String alamat, String jenisDonatur, String namaKontak,
                              String noTelp){
        ContentValues values = new ContentValues();
        values.put(Donatur.KEY_nama, nama);
        values.put(Donatur.KEY_alamat, alamat);
        values.put(Donatur.KEY_jenisDonatur, jenisDonatur);
        values.put(Donatur.KEY_namaKontak, namaKontak);
        values.put(Donatur.KEY_noTelp, noTelp);
        //long insertId =
        mDatabase.insert(Donatur.TABLE, null, values);
        //long insertId = mDatabase.insert(Anggota.TABLE, null, values);
        //Cursor cursor = mDatabase.query(Donatur.TABLE,
        //mAllColumns, Donatur.KEY_ID_donatur + " = " + insertId, null, null, null, null);
        //cursor.moveToFirst();
        //Donatur newDonatur = cursorToDonatur(cursor);
        //cursor.close();
    }

    public void deleteDonatur(Donatur donatur){
        long id = donatur.getId_donatur();
        mDatabase.delete(Donatur.TABLE, Donatur.KEY_ID_donatur + " = " + id, null);
        //System.out.println("Donatur dengan id = "+id+" telah dihapus");
    }

    public List<Donatur> getAllDonatur() {
        List<Donatur> listDonatur = new ArrayList<Donatur>();

        Cursor cursor = mDatabase.query(Donatur.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Donatur donatur = cursorToDonatur(cursor);
            listDonatur.add(donatur);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDonatur;
    }

    public Donatur getDonatur(int id) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Donatur.TABLE, new String[]
                        { Donatur.KEY_ID_donatur, Donatur.KEY_nama, Donatur.KEY_alamat, Donatur.KEY_jenisDonatur,
                                Donatur.KEY_namaKontak, Donatur.KEY_noTelp},
                Donatur.KEY_ID_donatur + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Donatur donatur = new Donatur(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));

        //db.close();
        cursor.close();
        return donatur;
    }

    public int updateDonatur(Donatur donatur) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Donatur.KEY_ID_donatur, donatur.getId_donatur());
        values.put(Donatur.KEY_nama, donatur.getNama());
        values.put(Donatur.KEY_alamat, donatur.getAlamat());
        values.put(Donatur.KEY_jenisDonatur, donatur.getJenisDonatur());
        values.put(Donatur.KEY_namaKontak, donatur.getNamaKontak());
        values.put(Donatur.KEY_noTelp, donatur.getNoTelp());

        int rowsAffected = db.update(donatur.TABLE, values, donatur.KEY_ID_donatur + "=?", new String[] { String.valueOf(donatur.getId_donatur()) });
        db.close();

        return rowsAffected;
    }

    public SummaryBuku summaryBuku() {

        String query = "SELECT " +
                "jenisDonatur, " +
                "count(jenisDonatur) " +
                "FROM Buku B, Donatur P WHERE B.id_donatur = P.id_donatur GROUP BY P.jenisDonatur";
        Cursor cursor = mDatabase.rawQuery(query, null);
        //Log.d("cek", cursor.toString());
        int individu = 0;
        int organisasi= 0;
        int beli_sendiri = 0;
        int yayasan = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(0).toString().equalsIgnoreCase("Individu"))
                individu = cursor.getInt(1);
            if(cursor.getString(0).toString().equalsIgnoreCase("Organisasi"))
                organisasi = cursor.getInt(1);
            if(cursor.getString(0).toString().equalsIgnoreCase("1001buku"))
                yayasan = cursor.getInt(1);
            if(cursor.getString(0).toString().equalsIgnoreCase("Taman Baca"))
                beli_sendiri = cursor.getInt(1);
            cursor.moveToNext();
        }
        SummaryBuku sum = new SummaryBuku(individu, organisasi, beli_sendiri, yayasan);
        // make sure to close the cursor
        cursor.close();
        return sum;
    }

    private Donatur cursorToDonatur(Cursor cursor) {
        Donatur donatur = new Donatur( (int)cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));

        return donatur;
    }
}