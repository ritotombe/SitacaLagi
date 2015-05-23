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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class UbahTbSekitarActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_tb_sekitar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_tb_sekitar, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private TbSekitarDAO tbSekitarDao;
        private TamanBacaDAO tamanBacaDao;
        private TamanBaca tamanBaca;
        private View rootView;
        private EditText tambahTbSekitarNama, tambahTbSekitarAlamat, tambahTbSekitarTwitter,
                tambahTbSekitarFacebook, tambahTbSekitarNoTelp;

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

            rootView = inflater.inflate(R.layout.fragment_ubah_tb_sekitar, container, false);

            //Cuman setting layout
            tambahTbSekitarNama = (EditText)rootView.findViewById(R.id.tambahTbSekitarNama);
            tambahTbSekitarAlamat = (EditText) rootView.findViewById(R.id.tambahTbSekitarAlamat);
            tambahTbSekitarTwitter = (EditText) rootView.findViewById(R.id.tambahTbSekitarTwitter);
            tambahTbSekitarFacebook = (EditText) rootView.findViewById(R.id.tambahTbSekitarFacebook);
            tambahTbSekitarNoTelp = (EditText) rootView.findViewById(R.id.tambahTbSekitarNoTelp);

            tbSekitarDao = new TbSekitarDAO(rootView.getContext());
            tamanBacaDao = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDao.getAllTamanBaca().get(0);

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");

            //Mengisi field secara otomatis
            final TbSekitar tbSekitar = tbSekitarDao.getTbSekitar(id);
            tambahTbSekitarNama.setText(tbSekitar.getNama());
            tambahTbSekitarAlamat.setText(tbSekitar.getAlamat());
            tambahTbSekitarTwitter.setText(tbSekitar.getTwitter());
            tambahTbSekitarFacebook.setText(tbSekitar.getFacebook());
            tambahTbSekitarNoTelp.setText(tbSekitar.getNo_telepon());

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){

                    boolean cek=true;

                    if(String.valueOf(tambahTbSekitarNama.getText()).equalsIgnoreCase("")){
                        cek = false;
                        errorToast("Kesalahan : Nama taman baca belum terisi.");
                    }
                    if(String.valueOf(tambahTbSekitarAlamat.getText()).equalsIgnoreCase("")){
                        cek = false;
                        errorToast("Kesalahan : Alamat taman baca belum terisi.");
                    }
                    if(String.valueOf(tambahTbSekitarNoTelp.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Nomor telepon belum terisi.");
                    }
                    if(cek) {
                        tbSekitar.setNama(String.valueOf(tambahTbSekitarNama.getText()));
                        tbSekitar.setAlamat(String.valueOf(tambahTbSekitarAlamat.getText()));
                        tbSekitar.setTwitter(String.valueOf(tambahTbSekitarTwitter.getText()));
                        tbSekitar.setFacebook(String.valueOf(tambahTbSekitarFacebook.getText()));
                        tbSekitar.setNo_telepon(String.valueOf(tambahTbSekitarNoTelp.getText()));

                        tbSekitarDao.updateTbSekitar(tbSekitar);
                        Toast.makeText(
                                rootView.getContext(),
                                "Taman baca telah diubah.",
                                Toast.LENGTH_SHORT
                        ).show();
                        tbSekitarDao.close();
                        tamanBacaDao.close();
                        Intent intent = new Intent(rootView.getContext(), TbSekitarActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    }

            });

            return rootView;
        }
    }
}
