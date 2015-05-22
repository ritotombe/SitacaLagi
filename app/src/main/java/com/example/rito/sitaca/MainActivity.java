package com.example.rito.sitaca;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

  //  DBHelper DB = new DBHelper(getApplicationContext());
    private DrawerLayout mDrawerLayout;
    private NonScrollListView mDrawerListTambahan;
    private NonScrollListView mDrawerListUtama;
    private ActionBarDrawerToggle mDrawerToggle;

    SharedPreferences pref;
    private String[] menuUtama={"Peminjaman","Log Harian","Buku","Anggota","Pertukaran Buku"};
    private String[] menuTambahan={"Kategori","Donatur","Kegiatan","Taman Baca","Pengguna", "Pengumuman", "Kirim Laporan"};


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListTambahan = (NonScrollListView) findViewById(R.id.drawer_menu_tambahan);
       // mDrawerListUtama = (NonScrollListView) findViewById(R.id.drawer_menu_utama);
        pref = this.getSharedPreferences("kirim", 0);
        Log.d("idtb",""+ pref.getInt("id_tb",-1));
        Log.d("idtbuser",""+ pref.getInt("id_user",-1));
        // set a custom shadow that overlays the main content when the drawer opens
       // mDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
      /*  mDrawerListUtama.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, menuUtama));
        mDrawerListUtama.setOnItemClickListener(new ListView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
             Intent intent;
                switch (position){
                case 0:
                    intent = new Intent(getApplicationContext(), PeminjamanActivity.class);
                    startActivity(intent);
                    break;
                 case 1:
                    intent = new Intent(getApplicationContext(), LogHarianActivity.class);
                    startActivity(intent);
                    break;
                 case 2:
                    intent = new Intent(getApplicationContext(), BukuActivity.class);
                    startActivity(intent);
                    break;
                 case 3:
                    intent = new Intent(getApplicationContext(), AnggotaActivity.class);
                    startActivity(intent);
                    break;
                 case 4:
                    intent = new Intent(getApplicationContext(), PertukaranBukuActivity.class);
                    startActivity(intent);
                    break;
                }
              }
            }
        );*/
        mDrawerListTambahan.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, menuTambahan));
        mDrawerListTambahan.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(getApplicationContext(), KategoriActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), DonaturActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), KegiatanActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), UbahTamanBacaActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), UbahUserActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 5:
                        if(new Connection().checkConnection(getApplicationContext())) {
                            intent = new Intent(getApplicationContext(), PengumumanActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Kesalahan: Anda tidak tersambung ke internet",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        break;
                    case 6:
                        if(new Connection().checkConnection(getApplicationContext())) {
                            if (pref.getInt("kirim_user", -1) == 1 && pref.getInt("kirim_tb", -1) == 1) {
                                kirimLaporan2(2);
                            } else
                                kirimLaporan1();
                            break;
                        }
                        else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Kesalahan: Anda tidak tersambung ke internet",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                }
            }
        }
       );
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
       // Drawable drawer = (Drawable) (R.mipmap.ic_drawer);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
                return super.onOptionsItemSelected(item);

    }

    public void kirimLaporan1(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.kirim_laporan_1);
        dialog.setTitle("Pilihan Pengiriman Laporan");
        dialog.setCancelable(true);

        //kirim pengguna dan tb
        Button button1 = (Button) dialog.findViewById(R.id.kirim1);
        //kirim pengguna
        Button button2 = (Button) dialog.findViewById(R.id.kirim2);
        //impor
        Button button3 = (Button) dialog.findViewById(R.id.kirim3);
        button3.setVisibility(View.GONE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimLaporan2(0);
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimLaporan2(1);
                dialog.dismiss();
            }
        });

        /*button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimLaporan2(2);
                dialog.dismiss();
            }
        });*/


        dialog.show();
    }

    public void kirimLaporan2(final int mode)
    {
       final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.kirim_laporan_2);
        dialog.setTitle("Perhatian");
        dialog.setCancelable(true);
        final EditText pass = (EditText) dialog.findViewById(R.id.passKirim);
        Button kirim = (Button) dialog.findViewById(R.id.btnKirim);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userdao = new UserDAO(getApplicationContext());
                if(!pass.getText().toString().equalsIgnoreCase("")) {
                    if (userdao.getAllUser().get(0).getPassword().equals(pass.getText().toString())) {
                        kirimLaporan3(mode);
                        dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(dialog.getContext(), "Kata Sandi Salah", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(dialog.getContext(), "Kata Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void kirimLaporan3(final int mode)
    {
        switch (mode) {
            //pengguna dan tb
            case 0:
               buatUser(0);
            break;
            case 1:
                buatUser(1);
            break;
            case 2:
                buatLaporan();
            break;
        }
    }

    public void buatUser(final int mode)
    {
        final User user = new UserDAO(this).getAllUser().get(0);
        final TamanBaca tb = new TamanBacaDAO(this).getAllTamanBaca().get(0);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("aksi", "buat"));
        params.add(new BasicNameValuePair("nama", user.getNama()));
        params.add(new BasicNameValuePair("alamat", user.getAlamat()));
        params.add(new BasicNameValuePair("jabatan", user.getJabatan()));
        params.add(new BasicNameValuePair("notelp", user.getNoTelp()));
        params.add(new BasicNameValuePair("email", user.getEmail()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        RequestData requestData = new RequestData(
                "userdao.php",
                params,
                this,
                "Mengirim Data"){
            @Override
            protected void onPostExecute(JSONArray data) {
                pDialog.dismiss();
                Log.d("data",""+data);
                try {
                    pref.edit().putInt("id_user", Integer.parseInt(data.get(1).toString())).apply();
                    Toast.makeText(getApplicationContext(), data.get(0).toString(),Toast.LENGTH_SHORT).show();
                    if(mode == 0)
                    {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("aksi", "buat"));
                        params.add(new BasicNameValuePair("nama", tb.getNama()));
                        params.add(new BasicNameValuePair("alamat", tb.getAlamat()));
                        params.add(new BasicNameValuePair("twitter", tb.getTwitter()));
                        params.add(new BasicNameValuePair("facebook", tb.getFacebook()));
                        params.add(new BasicNameValuePair("email_user", user.getEmail()));

                        RequestData requestData = new RequestData(
                                "tbdao.php",
                                params,
                                MainActivity.this,
                                "Mengirim Data"){
                            @Override
                            protected void onPostExecute(JSONArray data1) {
                                pDialog.dismiss();
                                Log.d("iniData", ""+data1);
                                try {

                                    Toast.makeText(getApplicationContext(), data1.get(0).toString(),Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                try {

                                    pref.edit().putInt("id_tb", Integer.parseInt(data1.get(1).toString())).apply();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                pref.edit().putInt("kirim_tb", 1).apply();

                            }
                        };
                        requestData.execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pref.edit().putInt("kirim_user", 1).apply();
            }
        };
        requestData.execute();
    }

    public void buatLaporan()
    {
        BuatLaporan laporan = new BuatLaporan(pref.getInt("id_tb",-1), this);
        String ratingQuery = laporan.ratingBuku();
        String summaryQuery = laporan.summary();
        String kategoriQuery = laporan.kategori();
        String logHarianQuery = laporan.logHarian();
        String kegiatanQuery = laporan.kegiatan();

        Log.d("datak",ratingQuery);
        Log.d("datak",summaryQuery);
        Log.d("datak",kategoriQuery);
        Log.d("datak",logHarianQuery);
        Log.d("datak",kegiatanQuery);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("aksi", "kirim_laporan"));
        params.add(new BasicNameValuePair("id_tb", ""+pref.getInt("id_tb",-1)));
        params.add(new BasicNameValuePair("rating_query", laporan.ratingBuku()));
        params.add(new BasicNameValuePair("summary_query", laporan.summary()));
        params.add(new BasicNameValuePair("kategori_query", laporan.kategori()));
        params.add(new BasicNameValuePair("log_query", laporan.logHarian()));
        params.add(new BasicNameValuePair("kegiatan_query", laporan.kegiatan()));

        RequestData requestData = new RequestData(
                "kirimlaporan.php",
                params,
                MainActivity.this,
                "Mengirim Data"){
            @Override
            protected void onPostExecute(JSONArray data1) {
                pDialog.dismiss();
                Log.d("iniData", ""+data1);
                try {
                    Toast.makeText(getApplicationContext(), data1.get(0).toString(),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        requestData.execute();
        
    }

    //Navigasi ke tiap halaman utama

    public void showPeminjaman(View view){
        Intent intent = new Intent(this, PeminjamanActivity.class);
        startActivity(intent);
    }

    public void showLogHarian(View view){
        Intent intent = new Intent(this, LogHarianActivity.class);
        startActivity(intent);
    }
    public void showBuku(View view){
        Intent intent = new Intent(this, BukuActivity.class);
        startActivity(intent);
    }
    public void showKeanggotaan(View view){
       Intent intent = new Intent(this, AnggotaActivity.class);
       startActivity(intent);
    }
    public void showPertukaranBuku(View view){
        Intent intent = new Intent(this, KelolaPertukaranBukuActivity.class);
        startActivity(intent);
    }
    public void showTbSekitar(View view){
        Intent intent = new Intent(this, TbSekitarActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
