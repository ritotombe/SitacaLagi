package com.example.rito.sitaca;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


public class UbahAnggotaActivity extends ActionBarActivity implements DatePickerFragment.TheListener{
    String date;
    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_anggota);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_anggota, menu);
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

    @Override
    public void returnDate(String date) {
        this.date = date;
        fragment.buatAnggotaTanggalLahir.setText(this.date);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private AnggotaDAO anggotaDao;
        private TamanBaca tamanBaca;
        private TamanBacaDAO tamanBacaDao;
        List<Anggota> listAnggota = new ArrayList<Anggota>();
        List<TamanBaca> listTamanBaca = new ArrayList<TamanBaca>();
        private int idTamanBaca;
        private View rootView;
        private EditText buatAnggotaNamaLengkap, buatAnggotaNamaPanggilan, buatAnggotaTanggalLahir, buatAnggotaAlamat,
                buatAnggotaNomorTelepon, buatAnggotaSekolah, buatAnggotaKelas, buatAnggotaJumlahPoin;
        private Spinner status, jenisKelamin;
        String day, month, year;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_ubah_anggota, container, false);
            // Layout
            buatAnggotaNamaLengkap = (EditText) rootView.findViewById(R.id.buatAnggotaNamaLengkap);
            buatAnggotaNamaPanggilan = (EditText) rootView.findViewById(R.id.buatAnggotaNamaPanggilan);
            buatAnggotaTanggalLahir = (EditText) rootView.findViewById(R.id.buatAnggotaTanggalLahir);
            buatAnggotaAlamat = (EditText) rootView.findViewById(R.id.buatAnggotaAlamat);
            jenisKelamin = (Spinner) rootView.findViewById(R.id.buatAnggotaJenisKelamin);
            buatAnggotaNomorTelepon = (EditText) rootView.findViewById(R.id.buatAnggotaNomorTelepon);
            buatAnggotaSekolah = (EditText) rootView.findViewById(R.id.buatAnggotaSekolah);
            buatAnggotaKelas = (EditText) rootView.findViewById(R.id.buatAnggotaKelas);
            status = (Spinner) rootView.findViewById(R.id.buatAnggotaStatus);
            buatAnggotaJumlahPoin = (EditText) rootView.findViewById(R.id.buatAnggotaJumlahPoin);

            anggotaDao = new AnggotaDAO(rootView.getContext());
            tamanBacaDao = new TamanBacaDAO(rootView.getContext());
            tamanBaca = tamanBacaDao.getAllTamanBaca().get(0);

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");

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
            jenisKelamin.setSelection(listJenisKelamin.size()-1, true);

            //buat spinner status
            String[] statusArray = {"Tidak Aktif"};
            List<String> listStatus = new ArrayList<String>
                    (Arrays.asList(statusArray));
            listStatus.add("Aktif");
            ArrayAdapter<String> statusSpinnerAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_item_spinner_empty,
                            //R.id.emptySpinnerValue,
                            listStatus
                    );
            statusSpinnerAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
            status.setAdapter(statusSpinnerAdapter);
            status.setSelection(listStatus.size()-1, true);

            //Mengisi field secara otomatis
            final Anggota anggota = anggotaDao.getAnggota(id);
            Log.d("tesanggota", "" + anggota.getId_anggota());
            buatAnggotaNamaLengkap.setText(anggota.getNama_lengkap());
            buatAnggotaNamaPanggilan.setText(anggota.getNama_panggilan());
            buatAnggotaAlamat.setText(anggota.getAlamat());
            buatAnggotaNomorTelepon.setText(anggota.getNo_telp());
            buatAnggotaSekolah.setText(anggota.getSekolah());
            buatAnggotaKelas.setText(anggota.getKelas());
            buatAnggotaTanggalLahir.setText(anggota.getTanggal_lahir());
            //buatAnggotaStatus.setText(anggota.getStatus());
            buatAnggotaJumlahPoin.setText(""+anggota.getJumlahPoin());

            if(anggota.getJenis_kelamin().equals("Laki-laki")) {
                jenisKelamin.setSelection(0);
            }
            else{
                jenisKelamin.setSelection(1);
            }

            if(anggota.getStatus().equals("Aktif")){
                status.setSelection(1);
            }
            else{
                status.setSelection(0);
            }

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
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

                        anggota.setNama_lengkap(String.valueOf(buatAnggotaNamaLengkap.getText()));
                        anggota.setNama_panggilan(String.valueOf(buatAnggotaNamaPanggilan.getText()));
                        anggota.setAlamat(String.valueOf(buatAnggotaAlamat.getText()));
                        anggota.setJenis_kelamin(jenisKelamin.getSelectedItem().toString());
                        anggota.setNo_telp(String.valueOf(buatAnggotaNomorTelepon.getText()));
                        anggota.setSekolah(String.valueOf(buatAnggotaSekolah.getText()));
                        anggota.setKelas(String.valueOf(buatAnggotaKelas.getText()));
                        anggota.setTanggal_lahir(String.valueOf(buatAnggotaTanggalLahir.getText()));
                        anggota.setStatus(status.getSelectedItem().toString());
                        anggota.setJumlahPoin(Integer.parseInt(String.valueOf(buatAnggotaJumlahPoin.getText())));

                        anggotaDao.updateAnggota(anggota);
                        Toast.makeText(
                                rootView.getContext(),
                                "Anggota telah diubah.",
                                Toast.LENGTH_SHORT
                        ).show();
                        anggotaDao.close();
                        tamanBacaDao.close();
                        Intent intent = new Intent(rootView.getContext(), AnggotaActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            });
///
            return rootView;
        }
    }
}
