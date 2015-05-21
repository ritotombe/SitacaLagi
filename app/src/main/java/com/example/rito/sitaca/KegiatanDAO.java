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
 * Created by AYU ARYSTYANA on 08/05/2015.
 */
public class KegiatanDAO {//Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {Kegiatan.KEY_ID_kegiatan,
                                    Kegiatan.KEY_ID_tamanBaca,
                                    Kegiatan.KEY_nama,
                                    Kegiatan.KEY_tanggal,
                                    Kategori.KEY_deskripsi};

    public KegiatanDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("Kegiatan", "Exception : " + e.getMessage());
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

    public void createKegiatan(int id_tamanBaca, String nama, String tanggal, String deskripsi){
        ContentValues values = new ContentValues();
        values.put(Kegiatan.KEY_ID_tamanBaca, id_tamanBaca);
        values.put(Kegiatan.KEY_nama, nama);
        values.put(Kegiatan.KEY_tanggal, tanggal);
        values.put(Kegiatan.KEY_deskripsi, deskripsi);
        //long insertId =
        mDatabase.insert(Kegiatan.TABLE, null, values);
        //long insertId =
        //mDatabase.insert(Anggota.TABLE, null, values);
        //Cursor cursor = mDatabase.query(Kategori.TABLE,
        //        mAllColumns, Kategori.KEY_ID_kategori + " = " + insertId, null, null, null, null);
        //cursor.moveToFirst();
        // Kategori newKategori = cursorToKategori(cursor);
        //cursor.close();
    }

    public void deleteKegiatan(Kegiatan kegiatan){
        long id = kegiatan.getId_kegiatan();
        mDatabase.delete(Kegiatan.TABLE, Kegiatan.KEY_ID_kegiatan +" = "+ id, null);
        System.out.println("Kegiatan dengan id = "+id+" telah dihapus");
    }

    public List<Kegiatan> getAllKegiatan() {
        List<Kegiatan> listKegiatan = new ArrayList<Kegiatan>();

        Cursor cursor = mDatabase.query(Kegiatan.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Kegiatan kegiatan = cursorToKegiatan(cursor);
            listKegiatan.add(kegiatan);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listKegiatan;
    }
    public Kegiatan getKegiatan(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Kegiatan.TABLE, new String[]
                        { Kegiatan.KEY_ID_kegiatan, Kegiatan.KEY_ID_tamanBaca, Kegiatan.KEY_nama, Kegiatan.KEY_tanggal,
                          Kegiatan.KEY_deskripsi},
                          Kegiatan.KEY_ID_kegiatan + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Kegiatan kegiatan = new Kegiatan(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        //db.close();
        cursor.close();
        return kegiatan;
    }

    public int updateKegiatan(Kegiatan kegiatan) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Kegiatan.KEY_ID_kegiatan, kegiatan.getId_kegiatan());
        values.put(Kegiatan.KEY_ID_tamanBaca, kegiatan.getId_tamanBaca());
        values.put(Kegiatan.KEY_nama, kegiatan.getNama());
        values.put(Kegiatan.KEY_tanggal, kegiatan.getTanggal());
        values.put(Kegiatan.KEY_deskripsi, kegiatan.getDeskripsi());

        int rowsAffected = db.update(kegiatan.TABLE, values, kegiatan.KEY_ID_kegiatan + "=?", new String[] { String.valueOf(kegiatan.getId_kegiatan()) });
        //db.close();

        return rowsAffected;
    }

    private Kegiatan cursorToKegiatan(Cursor cursor) {
        Kegiatan kegiatan = new Kegiatan(
                (int)cursor.getLong(0),
                (int)cursor.getLong(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));


        // get Buku or Anggota by id
        /*long PeminjamanId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setCompany(company);*/

        return kegiatan;
    }

}
