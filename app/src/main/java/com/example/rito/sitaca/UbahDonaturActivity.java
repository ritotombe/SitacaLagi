package com.example.rito.sitaca;

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


public class UbahDonaturActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_donatur);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_donatur, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private DonaturDAO donaturDao;
        private TamanBaca tamanBaca;
        private TamanBacaDAO tamanBacaDao;
        List<Donatur> listDonatur = new ArrayList<Donatur>();
        List<TamanBaca> listTamanBaca = new ArrayList<TamanBaca>();
        private int idTamanBaca;
        private View rootView;
        private EditText tambahDonaturNama, tambahDonaturAlamat, tambahDonaturNamaKontak, tambahDonaturNoTelp;
        private Spinner jenisDonatur;

        public PlaceholderFragment() {
        }

        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_ubah_donatur, container, false);
            // Layout
            tambahDonaturNama = (EditText) rootView.findViewById(R.id.tambahDonaturNama);
            tambahDonaturAlamat = (EditText) rootView.findViewById(R.id.tambahDonaturAlamat);
            jenisDonatur = (Spinner) rootView.findViewById(R.id.tambahDonaturJenisDonatur);
            tambahDonaturNamaKontak = (EditText) rootView.findViewById(R.id.tambahDonaturNamaKontak);
            tambahDonaturNoTelp = (EditText) rootView.findViewById(R.id.tambahDonaturNoTelp);

            donaturDao = new DonaturDAO(rootView.getContext());
            tamanBacaDao = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDao.getAllTamanBaca().get(0);

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");

            //Buat spinner
            String[] jenisDonaturArray = {"Individu","1001buku","Organisasi","Taman Baca"};
            List<String> listJenisDonatur = new ArrayList<String>
                    (Arrays.asList(jenisDonaturArray));
            listJenisDonatur.add("Jenis Donatur");
            ArrayAdapter<String> jenisDonaturSpinnerAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            //R.id.emptySpinnerValue,
                            listJenisDonatur
                    );
            jenisDonaturSpinnerAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            jenisDonatur.setAdapter(jenisDonaturSpinnerAdapter);
            jenisDonatur.setSelection(listJenisDonatur.size()-1, true);

            //Mengisi field secara otomatis
            final Donatur donatur = donaturDao.getDonatur(id);

            tambahDonaturNama.setText(donatur.getNama());
            tambahDonaturAlamat.setText(donatur.getAlamat());
            tambahDonaturNamaKontak.setText(donatur.getNamaKontak());
            tambahDonaturNoTelp.setText(String.valueOf(donatur.getNoTelp()));

            if(donatur.getJenisDonatur().equals("Individu")) {
                jenisDonatur.setSelection(0);
            }else if(donatur.getJenisDonatur().equals("1001buku")) {
                jenisDonatur.setSelection(1);
            }else if(donatur.getJenisDonatur().equals("Organisasi")) {
                jenisDonatur.setSelection(2);
            }else{
                jenisDonatur.setSelection(3);
            }

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){

                    boolean tes = true;

                    if(String.valueOf(tambahDonaturNama.getText()).equalsIgnoreCase("")){
                        tes =  false;
                        errorToast("Kesalahan : Nama donatur belum terisi.");
                    }
                    if(String.valueOf(tambahDonaturAlamat.getText()).equalsIgnoreCase("")){
                        tes =  false;
                        errorToast("Kesalahan : Alamat donatur belum terisi.");
                    }
                    if(jenisDonatur.getSelectedItem().toString().equalsIgnoreCase("Jenis Donatur*")) {
                        tes = false;
                        errorToast("Kesalahan : Jenis donatur belum dipilih.");
                    }
                    if(String.valueOf(tambahDonaturNamaKontak.getText()).equalsIgnoreCase("")){
                        tes =  false;
                        errorToast("Kesalahan : Nama kontak donatur belum terisi.");
                    }
                    if(String.valueOf(tambahDonaturNoTelp.getText()).equalsIgnoreCase("")){
                        tes =  false;
                        errorToast("Kesalahan : Nomor telepon donatur belum terisi.");
                    }
                    if(tes) {

                        donatur.setNama(String.valueOf(tambahDonaturNama.getText()));
                        donatur.setAlamat(String.valueOf(tambahDonaturAlamat.getText()));
                        donatur.setJenisDonatur(jenisDonatur.getSelectedItem().toString());
                        donatur.setNamaKontak(String.valueOf(tambahDonaturNamaKontak.getText()));
                        donatur.setNoTelp(String.valueOf(tambahDonaturNoTelp.getText()));

                        donaturDao.updateDonatur(donatur);
                        Toast.makeText(
                                rootView.getContext(),
                                "Donatur telah diubah.",
                                Toast.LENGTH_SHORT
                        ).show();
                        donaturDao.close();
                        tamanBacaDao.close();
                        Intent intent = new Intent(rootView.getContext(), DonaturActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });

            return rootView;
        }
    }
}
