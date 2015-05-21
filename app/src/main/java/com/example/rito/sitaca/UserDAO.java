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

public class UserDAO {

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {User.KEY_ID_user,
                                    User.KEY_nama,
                                    User.KEY_alamat,
                                    User.KEY_jabatan,
                                    User.KEY_noTelp,
                                    User.KEY_email,
                                    User.KEY_password};
    public UserDAO(Context context){
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        //open database
        try{
            open();
        }
        catch (SQLException e) {
            Log.e("User", "Exception : " + e.getMessage());
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

    public void createUser(String nama, String alamat, String jabatan, String noTelp, String email, String password){
        ContentValues values = new ContentValues();
        values.put(User.KEY_nama, nama);
        values.put(User.KEY_alamat, alamat);
        values.put(User.KEY_jabatan, jabatan);
        values.put(User.KEY_noTelp, noTelp);
        values.put(User.KEY_email, email);
        values.put(User.KEY_password, password);
        //long insertId =
                mDatabase.insert(User.TABLE, null, values);
       /* Cursor cursor = mDatabase.query(User.TABLE,
                mAllColumns, User.KEY_ID_user + " = " + insertId, null, null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();*/
    }

    public void deleteUser(User user){
        long id = user.getId_user();
        mDatabase.delete(User.TABLE, User.KEY_ID_user +" = "+ id, null);
        System.out.println("User dengan id = "+id+" telah dihapus");
    }

    public List<User> getAllUser() {
        List<User> listUser = new ArrayList<User>();

        Cursor cursor = mDatabase.query(User.TABLE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            listUser.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listUser;
    }
    public User getUser(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE, new String[]
                        { User.KEY_ID_user, User.KEY_nama, User.KEY_alamat, User.KEY_jabatan, User.KEY_noTelp, User.KEY_email, User.KEY_password},
                          User.KEY_ID_user + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
        db.close();
        cursor.close();
        return user;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.KEY_ID_user, user.getId_user());
        values.put(User.KEY_nama, user.getNama());
        values.put(User.KEY_alamat, user.getAlamat());
        values.put(User.KEY_jabatan, user.getJabatan());
        values.put(User.KEY_noTelp, user.getNoTelp());
        values.put(User.KEY_email, user.getEmail());
        values.put(User.KEY_password, user.getPassword());

        int rowsAffected = db.update(user.TABLE, values, user.KEY_ID_user + "=?", new String[] { String.valueOf(user.getId_user()) });
        db.close();

        return rowsAffected;
    }

    private User cursorToUser(Cursor cursor) {
         User user = new User( (int)cursor.getLong(0),
                 cursor.getString(1),
                 cursor.getString(2),
                 cursor.getString(3),
                 cursor.getString(4),
                 cursor.getString(5),
                 cursor.getString(6));


        // get Buku or Anggota by id
        /*long PeminjamanId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setCompany(company);*/

        return user;
    }
}
