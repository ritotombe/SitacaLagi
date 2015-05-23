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


public class TambahTbSekitarActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_tb_sekitar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        setContentView(R.layout.activity_tambah_tb_sekitar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_tb_sekitar, menu);
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

        private List<TbSekitar> listTbSekitar;
        private TbSekitarDAO mTbSekitarDao;

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

        //pesan error

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_tambah_tb_sekitar, container, false);

            //Cuman setting layout
            tambahTbSekitarNama = (EditText)rootView.findViewById(R.id.tambahTbSekitarNama);
            tambahTbSekitarAlamat = (EditText) rootView.findViewById(R.id.tambahTbSekitarAlamat);
            tambahTbSekitarTwitter = (EditText) rootView.findViewById(R.id.tambahTbSekitarTwitter);
            tambahTbSekitarFacebook = (EditText) rootView.findViewById(R.id.tambahTbSekitarFacebook);
            tambahTbSekitarNoTelp = (EditText) rootView.findViewById(R.id.tambahTbSekitarNoTelp);

            tbSekitarDao = new TbSekitarDAO(rootView.getContext());
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

                }*/


                    mTbSekitarDao = new TbSekitarDAO(rootView.getContext());
                    listTbSekitar = mTbSekitarDao.getAllTbSekitar();

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
                        errorToast("Nomor telepon belum terisi");
                    }
                    if(cek) {

                        tbSekitarDao.createTbSekitar(
                                tamanBaca.getId_tamanBaca(),
                                String.valueOf(tambahTbSekitarNama.getText()),
                                String.valueOf(tambahTbSekitarAlamat.getText()),
                                String.valueOf(tambahTbSekitarTwitter.getText()),
                                String.valueOf(tambahTbSekitarFacebook.getText()),
                                String.valueOf(tambahTbSekitarNoTelp.getText())
                        );

                        Toast.makeText(
                                rootView.getContext(),
                                "Taman baca telah ditambahkan.",
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
