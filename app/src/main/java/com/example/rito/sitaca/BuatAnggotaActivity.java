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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class BuatAnggotaActivity extends ActionBarActivity implements DatePickerFragment.TheListener{
    String date;
    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_anggota);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        setContentView(R.layout.activity_buat_anggota);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buat_anggota, menu);
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
        fragment.buatAnggotaTanggalLahir.setText(this.date);
    }

    public String getDate()
    {
        return date;

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private EditText buatAnggotaNamaLengkap, buatAnggotaNamaPanggilan, buatAnggotaTanggalLahir, buatAnggotaAlamat,
                buatAnggotaNomorTelepon, buatAnggotaSekolah, buatAnggotaKelas;
        private Spinner jenisKelamin;
        private AnggotaDAO anggotaDAO;
        private TamanBacaDAO tamanBacaDAO;
        private TamanBaca tamanBaca;
        private View rootView;
        String day;
        String month;
        String year;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
          rootView = inflater.inflate(R.layout.fragment_buat_anggota, container, false);

            //Cuman setting layout
            buatAnggotaNamaLengkap = (EditText) rootView.findViewById(R.id.buatAnggotaNamaLengkap);
            buatAnggotaNamaPanggilan = (EditText) rootView.findViewById(R.id.buatAnggotaNamaPanggilan);
            buatAnggotaTanggalLahir = (EditText) rootView.findViewById(R.id.buatAnggotaTanggalLahir);
            buatAnggotaAlamat = (EditText) rootView.findViewById(R.id.buatAnggotaAlamat);
            jenisKelamin = (Spinner) rootView.findViewById(R.id.buatAnggotaJenisKelamin);
            buatAnggotaNomorTelepon = (EditText) rootView.findViewById(R.id.buatAnggotaNomorTelepon);
            buatAnggotaSekolah = (EditText) rootView.findViewById(R.id.buatAnggotaSekolah);
            buatAnggotaKelas = (EditText) rootView.findViewById(R.id.buatAnggotaKelas);

            //date picker tanggal lahir
            buatAnggotaTanggalLahir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment picker = new DatePickerFragment();

                    Bundle args = new Bundle();
                    if(!String.valueOf(buatAnggotaTanggalLahir.getText()).equalsIgnoreCase("")) {

                        StringTokenizer st = new StringTokenizer(String.valueOf(buatAnggotaTanggalLahir.getText()), "/");
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
                    picker.show(getActivity().getFragmentManager(), "Tanggal Lahir");
                }
            });

            //buat spinner jenis kelamin
            String[] jenisKelaminArray = {"Laki-laki", "Perempuan"};
            List<String> listJenisKelamin = new ArrayList<String>
                    (Arrays.asList(jenisKelaminArray));
            listJenisKelamin.add("Jenis Kelamin*");
            ArrayAdapter<String> jenisKelaminSpinnerAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            //R.id.emptySpinnerValue,
                            listJenisKelamin
                    );
            jenisKelaminSpinnerAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            jenisKelamin.setAdapter(jenisKelaminSpinnerAdapter);
            jenisKelamin.setSelection(listJenisKelamin.size() - 1, true);

            anggotaDAO = new AnggotaDAO(rootView.getContext());
            tamanBacaDAO = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDAO.getAllTamanBaca().get(0);

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
                    boolean cek = true;
                    if (String.valueOf(buatAnggotaNamaLengkap.getText()).equalsIgnoreCase("")) {
                        //errorToastNamaLengkap();
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Nama lengkap belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if(String.valueOf(buatAnggotaTanggalLahir.getText()).equalsIgnoreCase("")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Tanggal lahir belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if(String.valueOf(buatAnggotaAlamat.getText()).equalsIgnoreCase("")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Alamat belum terisi.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }if(jenisKelamin.getSelectedItem().toString().equalsIgnoreCase("Jenis Kelamin*")){
                        cek = false;
                        Toast.makeText(
                                rootView.getContext(),
                                "Kesalahan : Jenis kelamin belum dipilih.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    if(cek) {
                        anggotaDAO.createAnggota(tamanBaca.getId_tamanBaca(), String.valueOf(buatAnggotaNamaLengkap.getText()), String.valueOf(buatAnggotaNamaPanggilan.getText()),
                                String.valueOf(buatAnggotaTanggalLahir.getText()), String.valueOf(buatAnggotaAlamat.getText()), jenisKelamin.getSelectedItem().toString(),
                                String.valueOf(buatAnggotaNomorTelepon.getText()), String.valueOf(buatAnggotaSekolah.getText()), String.valueOf(buatAnggotaKelas.getText()));
                        Toast.makeText(
                                rootView.getContext(),
                                "Anggota telah ditambahkan.",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = new Intent(rootView.getContext(), AnggotaActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });

            return rootView;
        }
    }
}
