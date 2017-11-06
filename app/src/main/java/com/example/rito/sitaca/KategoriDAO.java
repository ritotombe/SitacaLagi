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

public class KategoriDAO {

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {Kategori.KEY_ID_kategori,
                                    Kategori.KEY_nama,
                                    Kategori.KEY_deskripsi};
    public KategoriDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("Kategori", "Exception : " + e.getMessage());
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

    public void createKategori(String nama, String deskripsi){
        ContentValues values = new ContentValues();
        values.put(Kategori.KEY_nama, nama);
        values.put(Kategori.KEY_deskripsi, deskripsi);
        //long insertId =
        mDatabase.insert(Kategori.TABLE, null, values);
        //long insertId =
                //mDatabase.insert(Anggota.TABLE, null, values);
        //Cursor cursor = mDatabase.query(Kategori.TABLE,
        //        mAllColumns, Kategori.KEY_ID_kategori + " = " + insertId, null, null, null, null);
        //cursor.moveToFirst();
       // Kategori newKategori = cursorToKategori(cursor);
        //cursor.close();
    }

    public void deleteKategori(Kategori kategori){
        long id = kategori.getId_kategori();
        mDatabase.delete(Kategori.TABLE, Kategori.KEY_ID_kategori +" = "+ id, null);
        System.out.println("Kategori dengan id = "+id+" telah dihapus");
    }

    public List<Kategori> getAllKategori() {
        List<Kategori> listKategori = new ArrayList<Kategori>();

        Cursor cursor = mDatabase.query(Kategori.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Kategori kategori = cursorToKategori(cursor);
            listKategori.add(kategori);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listKategori;
    }
    public Kategori getKategori(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Kategori.TABLE, new String[]
                { Kategori.KEY_ID_kategori, Kategori.KEY_nama, Kategori.KEY_deskripsi},
                Kategori.KEY_ID_kategori + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Kategori kategori = new Kategori(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        //db.close();
        cursor.close();
        return kategori;
    }

    public int updateKategori(Kategori kategori) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Kategori.KEY_ID_kategori, kategori.getId_kategori());
        values.put(Kategori.KEY_nama, kategori.getNama());
        values.put(Kategori.KEY_deskripsi, kategori.getDeskripsi());

        int rowsAffected = db.update(kategori.TABLE, values, kategori.KEY_ID_kategori + "=?", new String[] { String.valueOf(kategori.getId_kategori()) });
        //db.close();

        return rowsAffected;
    }

    private Kategori cursorToKategori(Cursor cursor) {
        Kategori kategori = new Kategori(
                (int)cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2));


        // get Buku or Anggota by id
        /*long PeminjamanId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setCompany(company);*/

        return kategori;
    }
}