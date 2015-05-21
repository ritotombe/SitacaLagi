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
 * Created by theo on 4/12/2015.
 */
public class TamanBacaDAO {
    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {TamanBaca.KEY_ID_tamanBaca,
            TamanBaca.KEY_ID_user,
            TamanBaca.KEY_nama,
            TamanBaca.KEY_alamat,
            TamanBaca.KEY_twitter,
            TamanBaca.KEY_facebook};

    public TamanBacaDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("TamanBaca", "Exception : " + e.getMessage());
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

    public void createTamanBaca(int id_user, String nama, String alamat, String twitter, String facebook){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TamanBaca.KEY_ID_user, id_user);
        values.put(TamanBaca.KEY_nama, nama);
        values.put(TamanBaca.KEY_alamat, alamat);
        values.put(TamanBaca.KEY_twitter, twitter);
        values.put(TamanBaca.KEY_facebook, facebook);
        //long insertId =
                mDatabase.insert(TamanBaca.TABLE, null, values);
        /*Cursor cursor = mDatabase.query(TamanBaca.TABLE,
                mAllColumns, TamanBaca.KEY_ID_tamanBaca + " = " + insertId, null, null, null, null, null);
        cursor.moveToFirst();
        User newTamanBaca = cursorToTamanBaca(cursor);
        cursor.close();*/
    }

    public void deleteTamanBaca(TamanBaca tamanBaca){
        long id = tamanBaca.getId_tamanBaca();
        mDatabase.delete(tamanBaca.TABLE, tamanBaca.KEY_ID_tamanBaca +" = "+ id, null);
        System.out.println("Taman Baca dengan id = "+id+" telah dihapus");
    }

    public List<TamanBaca> getAllTamanBaca() {
        List<TamanBaca> listTamanBaca = new ArrayList<TamanBaca>();

        Cursor cursor = mDatabase.query(TamanBaca.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                TamanBaca tamanBaca = cursorToTamanBaca(cursor);
            listTamanBaca.add(tamanBaca);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listTamanBaca;
    }

    public TamanBaca getTamanBaca(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(TamanBaca.TABLE, new String[]
                { TamanBaca.KEY_ID_tamanBaca, TamanBaca.KEY_ID_user, TamanBaca.KEY_nama,
                        TamanBaca.KEY_alamat, TamanBaca.KEY_twitter, TamanBaca.KEY_facebook}, TamanBaca.KEY_ID_tamanBaca + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        TamanBaca tamanBaca = new TamanBaca(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        db.close();
        cursor.close();
        return tamanBaca;
    }

    public int updateTamanBaca(TamanBaca tamanBaca) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TamanBaca.KEY_ID_tamanBaca, tamanBaca.getId_tamanBaca());
        values.put(TamanBaca.KEY_ID_user, tamanBaca.getId_user());
        values.put(TamanBaca.KEY_nama, tamanBaca.getNama());
        values.put(TamanBaca.KEY_alamat, tamanBaca.getAlamat());
        values.put(TamanBaca.KEY_twitter, tamanBaca.getTwitter());
        values.put(TamanBaca.KEY_facebook, tamanBaca.getFacebook());

        int rowsAffected = db.update(tamanBaca.TABLE, values, tamanBaca.KEY_ID_tamanBaca + "=?", new String[] { String.valueOf(tamanBaca.getId_tamanBaca()) });
        db.close();

        return rowsAffected;
    }


    private TamanBaca cursorToTamanBaca(Cursor cursor) {
        TamanBaca tamanBaca =
                new TamanBaca((int)cursor.getLong(0),
                        (int)cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
        return tamanBaca;
    }
}
