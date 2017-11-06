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
 * Created by Asus on 4/17/2015.
 */
public class TbSekitarDAO {

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            TbSekitar.KEY_ID_tb_sekitar,
            TbSekitar.KEY_ID_taman_baca,
            TbSekitar.KEY_nama,
            TbSekitar.KEY_alamat,
            TbSekitar.KEY_twitter,
            TbSekitar.KEY_facebook,
            TbSekitar.KEY_no_telepon};

    public TbSekitarDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("TbSekitar", "Exception : " + e.getMessage());
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

    public void createTbSekitar(int id_taman_baca, String nama, String alamat, String twitter, String facebook, String no_telepon){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TbSekitar.KEY_ID_taman_baca,id_taman_baca);
        values.put(TbSekitar.KEY_nama, nama);
        values.put(TbSekitar.KEY_alamat, alamat);
        values.put(TbSekitar.KEY_twitter, twitter);
        values.put(TbSekitar.KEY_facebook, facebook);
        values.put(TbSekitar.KEY_no_telepon, no_telepon);

        //long insertId =
        mDatabase.insert(TbSekitar.TABLE, null, values);
        //Cursor cursor = mDatabase.query(LogHarian.TABLE,
        //mAllColumns, LogHarian.KEY_ID_logHarian + " = " + insertId, null, null, null, null, null);
        //cursor.moveToFirst();
        //LogHarian newLogHarian = cursorToLogHarian(cursor);
        //cursor.close();
    }

    public void deleteTbSekitar(TbSekitar tbSekitar){
        long id = tbSekitar.getId_tb_sekitar();
        mDatabase.delete(TbSekitar.TABLE, TbSekitar.KEY_ID_tb_sekitar +" = "+ id, null);
        System.out.println("Taman Baca dengan id = "+id+" telah dihapus");
    }

    public List<TbSekitar> getAllTbSekitar() {
        List<TbSekitar> listTbSekitar = new ArrayList<TbSekitar>();

        Cursor cursor = mDatabase.query(TbSekitar.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TbSekitar tbSekitar = cursorToTbSekitar(cursor);
            listTbSekitar.add(tbSekitar);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listTbSekitar;
    }

    public TbSekitar getTbSekitar(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(TbSekitar.TABLE, new String[]
                        { TbSekitar.KEY_ID_tb_sekitar, TbSekitar.KEY_ID_taman_baca, TbSekitar.KEY_nama, TbSekitar.KEY_alamat, TbSekitar.KEY_twitter, TbSekitar.KEY_facebook, TbSekitar.KEY_no_telepon},
                TbSekitar.KEY_ID_tb_sekitar + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        TbSekitar tbSekitar = new TbSekitar(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
        //db.close();
        cursor.close();
        return tbSekitar;
    }

    public int updateTbSekitar(TbSekitar tbSekitar) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TbSekitar.KEY_ID_tb_sekitar, tbSekitar.getId_tb_sekitar());
        values.put(TbSekitar.KEY_ID_taman_baca, tbSekitar.getId_taman_baca());
        values.put(TbSekitar.KEY_nama, tbSekitar.getNama());
        values.put(TbSekitar.KEY_alamat, tbSekitar.getAlamat());
        values.put(TbSekitar.KEY_twitter, tbSekitar.getTwitter());
        values.put(TbSekitar.KEY_facebook, tbSekitar.getFacebook());
        values.put(TbSekitar.KEY_no_telepon, tbSekitar.getNo_telepon());

        int rowsAffected = db.update(tbSekitar.TABLE, values, tbSekitar.KEY_ID_tb_sekitar + "=?", new String[] { String.valueOf(tbSekitar.getId_tb_sekitar()) });
        //db.close();
        return rowsAffected;
    }

    private TbSekitar cursorToTbSekitar(Cursor cursor) {
        TbSekitar tbSekitar = new TbSekitar(
                (int)cursor.getLong(0),
                (int)cursor.getLong(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
        );


        // get Buku or Anggota by id
        /*long PeminjamanId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setCompany(company);*/

        return tbSekitar;
    }

}
//
