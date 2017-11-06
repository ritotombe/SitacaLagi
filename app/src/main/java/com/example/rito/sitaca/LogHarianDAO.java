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

public class LogHarianDAO {

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {LogHarian.KEY_ID_logHarian,
                                    LogHarian.KEY_ID_tamanBaca,
                                    LogHarian.KEY_tanggal,
                                    LogHarian.KEY_jumlah_kehadiran,
                                    LogHarian.KEY_realisasi_jamBuka,
                                    LogHarian.KEY_realisasi_jamTutup};
    public LogHarianDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("LogHarian", "Exception : " + e.getMessage());
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

    public void createLogHarian(int id_tamanBaca, String tanggal, int jumlah_kehadiran,
                                     String realisasi_jamBuka, String realisasi_jamTutup){
        ContentValues values = new ContentValues();
        values.put(LogHarian.KEY_ID_tamanBaca, id_tamanBaca);
        values.put(LogHarian.KEY_tanggal, tanggal);
        values.put(LogHarian.KEY_jumlah_kehadiran, jumlah_kehadiran);
        values.put(LogHarian.KEY_realisasi_jamBuka, realisasi_jamBuka);
        values.put(LogHarian.KEY_realisasi_jamTutup, realisasi_jamTutup);

        //long insertId =
                mDatabase.insert(LogHarian.TABLE, null, values);
        //Cursor cursor = mDatabase.query(LogHarian.TABLE,
                //mAllColumns, LogHarian.KEY_ID_logHarian + " = " + insertId, null, null, null, null, null);
        //cursor.moveToFirst();
        //LogHarian newLogHarian = cursorToLogHarian(cursor);
        //cursor.close();
    }

    public void deleteLogHarian(LogHarian logHarian){
        long id = logHarian.getId_logHarian();
        mDatabase.delete(LogHarian.TABLE, LogHarian.KEY_ID_logHarian +" = "+ id, null);
        //System.out.println("Log Harian dengan id = "+id+" telah dihapus");
    }

    public List<LogHarian> getAllLogHarian() {
        List<LogHarian> listLogHarian = new ArrayList<LogHarian>();

        Cursor cursor = mDatabase.query(LogHarian.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LogHarian logHarian = cursorToLogHarian(cursor);
            listLogHarian.add(logHarian);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listLogHarian;
    }

    public LogHarian getLogHarian(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(LogHarian.TABLE, new String[]
                { LogHarian.KEY_ID_logHarian, LogHarian.KEY_ID_tamanBaca, LogHarian.KEY_tanggal, LogHarian.KEY_jumlah_kehadiran,
                  LogHarian.KEY_realisasi_jamBuka, LogHarian.KEY_realisasi_jamTutup},
                  LogHarian.KEY_ID_logHarian + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        LogHarian logHarian = new LogHarian(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5));
          //db.close();
          //cursor.close();
        return logHarian;
    }

    public int updateLogHarian(LogHarian logHarian) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LogHarian.KEY_ID_logHarian, logHarian.getId_logHarian());
        values.put(LogHarian.KEY_ID_tamanBaca, logHarian.getId_tamanBaca());
        values.put(LogHarian.KEY_tanggal, logHarian.getTanggal());
        values.put(LogHarian.KEY_jumlah_kehadiran, logHarian.getJumlah_kehadiran());
        values.put(LogHarian.KEY_realisasi_jamBuka, logHarian.getRealisasi_jamBuka());
        values.put(LogHarian.KEY_realisasi_jamTutup, logHarian.getRealisasi_jamTutup());

        int rowsAffected = db.update(logHarian.TABLE, values, logHarian.KEY_ID_logHarian + "=?", new String[] { String.valueOf(logHarian.getId_logHarian()) });
        //db.close();

        return rowsAffected;
    }

    private LogHarian cursorToLogHarian(Cursor cursor) {
        LogHarian logHarian = new LogHarian( (int)cursor.getLong(0),
                (int)cursor.getLong(1),
                cursor.getString(2),
                (int)cursor.getLong(3),
                cursor.getString(4),
                cursor.getString(5));


        // get Buku or Anggota by id
        /*long PeminjamanId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setCompany(company);*/

        return logHarian;
    }
}