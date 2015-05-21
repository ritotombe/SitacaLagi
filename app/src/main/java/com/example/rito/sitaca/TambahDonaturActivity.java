package com.example.rito.sitaca;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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


public class TambahDonaturActivity extends ActionBarActivity {
    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_donatur);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        setContentView(R.layout.activity_tambah_donatur);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_donatur, menu);
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
        private TamanBacaDAO tamanBacaDAO;
        private TamanBaca tamanBaca;
        private View rootView;
        private Spinner jenisDonatur;
        private EditText tambahDonaturNama, tambahDonaturAlamat, tambahDonaturNamaKontak, tambahDonaturNoTelp;

        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_tambah_donatur, container, false);

            // Layout
            tambahDonaturNama = (EditText) rootView.findViewById(R.id.tambahDonaturNama);
            tambahDonaturAlamat = (EditText) rootView.findViewById(R.id.tambahDonaturAlamat);
            tambahDonaturNamaKontak = (EditText) rootView.findViewById(R.id.tambahDonaturNamaKontak);
            tambahDonaturNoTelp = (EditText) rootView.findViewById(R.id.tambahDonaturNoTelp);

            jenisDonatur = (Spinner) rootView.findViewById(R.id.tambahDonaturJenisDonatur);

            //Buat spinner
            String[] jenisDonaturArray = {"Individu","1001buku","Organisasi","Taman Baca"};
            List<String> listJenisDonatur = new ArrayList<String>
                    (Arrays.asList(jenisDonaturArray));
            listJenisDonatur.add("Jenis Donatur*");
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

            donaturDao = new DonaturDAO(rootView.getContext());
            tamanBacaDAO = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDAO.getAllTamanBaca().get(0);

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

                        donaturDao.createDonatur(
                                String.valueOf(tambahDonaturNama.getText()),
                                String.valueOf(tambahDonaturAlamat.getText()),
                                jenisDonatur.getSelectedItem().toString(),
                                String.valueOf(tambahDonaturNamaKontak.getText()),
                                String.valueOf(tambahDonaturNoTelp.getText()));

                        Intent intent = new Intent(rootView.getContext(), DonaturActivity.class);
                        startActivity(intent);

                        Toast.makeText(
                                rootView.getContext(),
                                "Donatur telah ditambahkan.",
                                Toast.LENGTH_SHORT
                        ).show();

                        getActivity().finish();
                    }
                }

            });

            return rootView;
        }
    }
}
