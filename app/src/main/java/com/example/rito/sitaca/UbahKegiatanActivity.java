package com.example.rito.sitaca;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class UbahKegiatanActivity extends ActionBarActivity implements DatePickerFragment.TheListener {
    String date;
    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_kegiatan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void returnDate(String date) {
        this.date = date;
        fragment.tanggal.setText(this.date);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private KegiatanDAO kegiatanDao;
        private TamanBaca tamanBaca;
        private TamanBacaDAO tamanBacaDao;
        List<Kegiatan> listKegiatan = new ArrayList<Kegiatan>();
        List<TamanBaca> listTamanBaca = new ArrayList<TamanBaca>();
        private int idTamanBaca;
        private View rootView;
        private EditText nama, tanggal, deskripsi;
        String day, month, year;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_ubah_kegiatan, container, false);
            // Layout
            nama = (EditText) rootView.findViewById(R.id.tambahKegiatanNama);
            tanggal = (EditText) rootView.findViewById(R.id.tambahKegiatanTanggal);
            deskripsi = (EditText) rootView.findViewById(R.id.tambahKegiatanDeskripsi);

            kegiatanDao = new KegiatanDAO(rootView.getContext());
            tamanBacaDao = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDao.getAllTamanBaca().get(0);

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");

            //date picker tanggal lahir
            tanggal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment picker = new DatePickerFragment();

                    Bundle args = new Bundle();
                    if(!String.valueOf(tanggal.getText()).equalsIgnoreCase("")) {

                        StringTokenizer st = new StringTokenizer(String.valueOf(tanggal.getText()), "/");
                        while(st.hasMoreTokens()) {
                            day = st.nextToken();
                            month = st.nextToken();
                            year = st.nextToken();
                        }

                        args.putString("day", day);
                        args.putString("month", month);
                        args.putString("year", year);

                    }
                    else{
                        args=null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Tanggal Kegiatan");
                }
            });

            //Mengisi field secara otomatis
            final Kegiatan kegiatan = kegiatanDao.getKegiatan(id);

            nama.setText(kegiatan.getNama());
            tanggal.setText(kegiatan.getTanggal());
            deskripsi.setText(kegiatan.getDeskripsi());

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    boolean cek = true;
                    if (String.valueOf(nama.getText()).equalsIgnoreCase("")) {
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Nama kegiatan belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if(String.valueOf(tanggal.getText()).equalsIgnoreCase("")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Tanggal kegiatan belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if(String.valueOf(deskripsi.getText()).equalsIgnoreCase("")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Deskripsi kegiatan belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    if(cek) {

                        kegiatan.setNama(String.valueOf(nama.getText()));
                        kegiatan.setTanggal(String.valueOf(tanggal.getText()));
                        kegiatan.setDeskripsi(String.valueOf(deskripsi.getText()));

                        kegiatanDao.updateKegiatan(kegiatan);
                        Toast.makeText(
                                rootView.getContext(),
                                "Kegiatan telah diubah.",
                                Toast.LENGTH_SHORT
                        ).show();
                        kegiatanDao.close();
                        tamanBacaDao.close();
                        Intent intent = new Intent(rootView.getContext(), KegiatanActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });
            return rootView;
        }
    }
}

