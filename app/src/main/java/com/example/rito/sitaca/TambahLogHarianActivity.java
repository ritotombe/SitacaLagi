package com.example.rito.sitaca;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

public class TambahLogHarianActivity extends ActionBarActivity implements DatePickerFragment.TheListener, TimePickerFragment.TheListener{
    String date, time;

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_log_harian);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        setContentView(R.layout.activity_tambah_log_harian);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_log_harian, menu);
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

    public String getDate()
    {
        return date;

    }

    @Override
    public void returnTime(String time) {
        this.time = time;
        if(fragment.realisasiJamBuka)
            fragment.tambahLogHarianRealisasiJamBuka.setText(this.time);
        else if(fragment.realisasiJamTutup)
            fragment.tambahLogHarianRealisasiJamTutup.setText(this.time);
    }

    public static class PlaceholderFragment extends Fragment {

        private LogHarianDAO logHarianDao;
        private TamanBacaDAO tamanBacaDao;
        private TamanBaca tamanBaca;
        private int idTamanBaca;
        private View rootView;
        private EditText tambahLogHarianTanggal, tambahLogHarianJumlahKehadiran, tambahLogHarianRealisasiJamBuka,
                tambahLogHarianRealisasiJamTutup;

        private boolean realisasiJamBuka = false, realisasiJamTutup = false;

        String day;
        String month;
        String year;
        String jamBuka;
        String menitBuka;
        String jamTutup;
        String menitTutup;

        int jamT, menitT, jamB, menitB;

        private List<LogHarian> listLogHarian;
        private LogHarianDAO mLogHarianDao;

        public PlaceholderFragment() {
        }

        void errorToastTgl(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Tanggal belum terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        void errorToastJamBuka(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Realisasi jam buka belum terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        void errorToastJamTutup(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Realisasi jam tutup belum terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        void errorToastTanggal(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Log harian sudah terisi.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        void errorToastPerbandinganTanggal(){
            Toast.makeText(
                    rootView.getContext(),
                    "Kesalahan : Jam buka harus sebelum jam tutup.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_tambah_log_harian, container, false);

            //Cuman setting layout
            tambahLogHarianTanggal = (EditText)rootView.findViewById(R.id.tambahLogHarianTanggal);
            tambahLogHarianJumlahKehadiran = (EditText) rootView.findViewById(R.id.tambahLogHarianJumlahKehadiran);
            tambahLogHarianRealisasiJamBuka = (EditText) rootView.findViewById(R.id.tambahLogHarianRealisasiJamBuka);
            tambahLogHarianRealisasiJamTutup = (EditText) rootView.findViewById(R.id.tambahLogHarianRealisasiJamTutup);

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

            logHarianDao = new LogHarianDAO(rootView.getContext());
            tamanBacaDao = new TamanBacaDAO(rootView.getContext());

            tamanBaca = tamanBacaDao.getAllTamanBaca().get(0);

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Lihat model dan Db handlernya ada method-methodnya untuk di sini

                /*if (!contactExists(contact)) {
                    Contacts.add(contact);
                    Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " has been added to your Contacts!", Toast.LENGTH_SHORT).show();
                    return;
wew
                }*/
                    mLogHarianDao = new LogHarianDAO(rootView.getContext());
                    listLogHarian = mLogHarianDao.getAllLogHarian();

                    int jmlKehadiran = 0;
                    boolean cek=true;
                    boolean ada=false;

                    if(listLogHarian.size()>0){
                        for(int i=0; i<listLogHarian.size(); i++){
                            if(String.valueOf(tambahLogHarianTanggal.getText()).equals(listLogHarian.get(i).getTanggal())){
                                ada = true;
                            }
                        }
                    }

                    if (!String.valueOf(tambahLogHarianJumlahKehadiran.getText()).equalsIgnoreCase("")) {
                        jmlKehadiran = Integer.parseInt(String.valueOf(tambahLogHarianJumlahKehadiran.getText()));
                    }

                    if(String.valueOf(tambahLogHarianTanggal.getText()).equalsIgnoreCase("")){
                        cek=false;
                        errorToastTgl();
                    }if(ada){
                        cek=false;
                        errorToastTanggal();
                    }if(String.valueOf(tambahLogHarianRealisasiJamBuka.getText()).equalsIgnoreCase("")) {
                        cek=false;
                        errorToastJamBuka();
                    }if(String.valueOf(tambahLogHarianRealisasiJamTutup.getText()).equalsIgnoreCase("")) {
                        cek=false;
                        errorToastJamTutup();
                    }if(!String.valueOf(tambahLogHarianRealisasiJamBuka.getText()).equalsIgnoreCase("") && !String.valueOf(tambahLogHarianRealisasiJamTutup.getText()).equalsIgnoreCase("")) {
                        StringTokenizer st1 = new StringTokenizer(String.valueOf(tambahLogHarianRealisasiJamBuka.getText()), ":");
                        while (st1.hasMoreTokens()) {
                            jamBuka = st1.nextToken();
                            menitBuka = st1.nextToken();
                        }
                        int jamB = Integer.parseInt(jamBuka);
                        int menitB = Integer.parseInt(menitBuka);

                        StringTokenizer st2 = new StringTokenizer(String.valueOf(tambahLogHarianRealisasiJamTutup.getText()), ":");
                        while (st2.hasMoreTokens()) {
                            jamTutup = st2.nextToken();
                            menitTutup = st2.nextToken();
                        }
                        int jamT = Integer.parseInt(jamTutup);
                        int menitT = Integer.parseInt(menitTutup);

                        if (jamT < jamB || menitT < menitB) {
                            cek = false;
                            errorToastPerbandinganTanggal();
                        }
                    }

                    if (cek) {
                        logHarianDao.createLogHarian(
                                tamanBaca.getId_tamanBaca(),
                                String.valueOf(tambahLogHarianTanggal.getText()),
                                jmlKehadiran,
                                String.valueOf(tambahLogHarianRealisasiJamBuka.getText()),
                                String.valueOf(tambahLogHarianRealisasiJamTutup.getText()));
                        Toast.makeText(
                                rootView.getContext(),
                                "Log harian telah ditambahkan.",
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