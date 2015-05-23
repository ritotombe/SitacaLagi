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


public class TambahKategoriActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kategori);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_kategori, menu);
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
        private KategoriDAO kategoriDAO;

        private View rootView;
        private EditText nama, deskripsi;
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


            rootView = inflater.inflate(R.layout.fragment_tambah_kategori, container, false);

            //Cuman setting layout

            nama = (EditText)rootView.findViewById(R.id.tambahKategoriNama);
            deskripsi = (EditText)rootView.findViewById(R.id.tambahKategoriDeskripsi);

            kategoriDAO = new KategoriDAO(rootView.getContext());

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                //@Override

                public void onClick(View view){

                    boolean tes = true;
                    if(String.valueOf(nama.getText()).equalsIgnoreCase("")){
                        tes = false;
                        errorToast("Kesalahan : Nama kategori belum terisi.");
                    }

                    if(String.valueOf(deskripsi.getText()).equalsIgnoreCase("")){
                        tes = false;
                        errorToast("Kesalahan : Deskripsi kategori belum terisi.");
                    }
                    if(tes){
                        kategoriDAO.createKategori(
                                String.valueOf(nama.getText()),
                                String.valueOf(deskripsi.getText())
                        );
                        Intent intent = new Intent(rootView.getContext(), KategoriActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(
                                rootView.getContext(),
                                "Kategori telah ditambahkan.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });
            return rootView;
        }
    }
}
