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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class UbahLogHarianActivity extends ActionBarActivity implements DatePickerFragment.TheListener, TimePickerFragment.TheListener{
    String date, time;
    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_log_harian);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_log_harian, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void returnDate(String date) {
        this.date = date;
        fragment.tambahLogHarianTanggal.setText(this.date);
    }

    @Override
    public void returnTime(String time) {
        this.time = time;
        if(fragment.realisasiJamBuka)
            fragment.tambahLogHarianRealisasiJamBuka.setText(this.time);
        else if(fragment.realisasiJamTutup)
            fragment.tambahLogHarianRealisasiJamTutup.setText(this.time);
    }

    /**
     * A placeholder fragment containing a simple view.
     * wew
     */
    public static class PlaceholderFragment extends Fragment {

        private LogHarianDAO logHarianDao;
        private TamanBacaDAO tamanBacaDao;
        private TamanBaca tamanBaca;
        private int idTamanBaca;
        private View rootView;
        private EditText tambahLogHarianTanggal, tambahLogHarianJumlahKehadiran, tambahLogHarianRealisasiJamBuka,
                tambahLogHarianRealisasiJamTutup;
        String day, month, year;
        String jamBuka;
        String menitBuka;
        String jamTutup;
        String menitTutup;

        private boolean realisasiJamBuka = false, realisasiJamTutup = false;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_ubah_log_harian, container, false);
            // Layout
            tambahLogHarianTanggal = (EditText)rootView.findViewById(R.id.tambahLogHarianTanggal);
            tambahLogHarianJumlahKehadiran = (EditText) rootView.findViewById(R.id.tambahLogHarianJumlahKehadiran);
            tambahLogHarianRealisasiJamBuka = (EditText) rootView.findViewById(R.id.tambahLogHarianRealisasiJamBuka);
            tambahLogHarianRealisasiJamTutup = (EditText) rootView.findViewById(R.id.tambahLogHarianRealisasiJamTutup);

            logHarianDao = new LogHarianDAO(rootView.getContext());
            tamanBacaDao = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDao.getAllTamanBaca().get(0);

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");

            //time picker realisasi jam Buka
            tambahLogHarianRealisasiJamBuka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment picker = new TimePickerFragment();

                    Bundle args = new Bundle();
                    if(!String.valueOf(tambahLogHarianRealisasiJamBuka.getText()).equalsIgnoreCase("")) {
                        StringTokenizer st = new StringTokenizer(String.valueOf(tambahLogHarianRealisasiJamBuka.getText()), ":");
                        while(st.hasMoreTokens()) {
                            jamBuka = st.nextToken();
                            menitBuka = st.nextToken();
                        }
                        args.putString("hour", jamBuka);
                        args.putString("minute", menitBuka);
                    }
                    else{
                        args=null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Waktu Buka");
                    realisasiJamBuka = true;
                    realisasiJamTutup = false;
                }
            });

            //time picker realisasi jam Tutup
            tambahLogHarianRealisasiJamTutup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment picker = new TimePickerFragment();

                    Bundle args = new Bundle();
                    if(!String.valueOf(tambahLogHarianRealisasiJamTutup.getText()).equalsIgnoreCase("")) {
                        StringTokenizer st = new StringTokenizer(String.valueOf(tambahLogHarianRealisasiJamTutup.getText()), ":");
                        while(st.hasMoreTokens()) {
                            jamTutup = st.nextToken();
                            menitTutup = st.nextToken();
                        }

                        args.putString("hour", jamTutup);
                        args.putString("minute", menitTutup);
                    }
                    else{
                        args=null;
                    }
                    picker.setArguments(args);
                    picker.show(getActivity().getFragmentManager(), "Waktu Tutup");
                    realisasiJamBuka = false;
                    realisasiJamTutup = true;
                }
            });

            //date picker tanggal
            tambahLogHarianTanggal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment picker = new DatePickerFragment();

                    Bundle args = new Bundle();
                    if(!String.valueOf(tambahLogHarianTanggal.getText()).equalsIgnoreCase("")) {

                        StringTokenizer st = new StringTokenizer(String.valueOf(tambahLogHarianTanggal.getText()), "/");
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
                    picker.show(getActivity().getFragmentManager(), "Tanggal");
                }
            });

            //Mengisi field secara otomatis
            final LogHarian logHarian = logHarianDao.getLogHarian(id);
            tambahLogHarianTanggal.setText(logHarian.getTanggal());
            tambahLogHarianJumlahKehadiran.setText(""+logHarian.getJumlah_kehadiran());
            tambahLogHarianRealisasiJamBuka.setText(logHarian.getRealisasi_jamBuka());
            tambahLogHarianRealisasiJamTutup.setText(logHarian.getRealisasi_jamTutup());

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    boolean cek = true;

                    if (String.valueOf(tambahLogHarianTanggal.getText()).equalsIgnoreCase("")) {
                        //errorToastNamaLengkap();
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Tanggal belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if (String.valueOf(tambahLogHarianRealisasiJamBuka.getText()).equalsIgnoreCase("")) {
                        //errorToastNamaLengkap();
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Realisasi jam buka belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if (String.valueOf(tambahLogHarianRealisasiJamTutup.getText()).equalsIgnoreCase("")) {
                        //errorToastNamaLengkap();
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Realisasi jam tutup belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    if(cek) {
                        logHarian.setTanggal(String.valueOf(tambahLogHarianTanggal.getText()));
                        if(String.valueOf(tambahLogHarianJumlahKehadiran.getText()).equalsIgnoreCase("")){
                            logHarian.setJumlah_kehadiran(0);
                        }
                        else {
                            logHarian.setJumlah_kehadiran(Integer.parseInt(String.valueOf(tambahLogHarianJumlahKehadiran.getText())));
                        }
                        logHarian.setRealisasi_jamBuka(String.valueOf(tambahLogHarianRealisasiJamBuka.getText()));
                        logHarian.setRealisasi_jamTutup(String.valueOf(tambahLogHarianRealisasiJamTutup.getText()));

                        logHarianDao.updateLogHarian(logHarian);
                        Toast.makeText(
                                rootView.getContext(),
                                "Log harian telah diubah.",
                                Toast.LENGTH_SHORT
                        ).show();
                        logHarianDao.close();
                        tamanBacaDao.close();
                        Intent intent = new Intent(rootView.getContext(), LogHarianActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });
            return rootView;
        }
    }
}
