package com.example.rito.sitaca;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class UbahUserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_user);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_user, menu);
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
        private UserDAO userDAO;
        private EditText nama, alamat, jabatan, noTelp, email, password, newPassword, rePassword;
        private View rootView;
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

        public final static boolean isValidEmail(CharSequence target) {
            if (target == null)
                return false;

            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_ubah_user, container, false);

            nama = (EditText) rootView.findViewById(R.id.ubahUserNamaLengkap);
            alamat = (EditText) rootView.findViewById(R.id.ubahUserAlamat);
            jabatan = (EditText) rootView.findViewById(R.id.ubahUserJabatan);
            noTelp = (EditText) rootView.findViewById(R.id.ubahUserNoTelp);
            email = (EditText) rootView.findViewById(R.id.ubahUserEmail);
            password = (EditText) rootView.findViewById(R.id.ubahUserKataSandi);
            newPassword = (EditText) rootView.findViewById(R.id.ubahUserKataSandiBaru);
            rePassword = (EditText) rootView.findViewById(R.id.ubahUserKonfirmasiKataSandi);

            final SharedPreferences pref;

            userDAO = new UserDAO(rootView.getContext());


            int id = userDAO.getAllUser().get(0).getId_user();
            pref = getActivity().getSharedPreferences("kirim", 0);

            final User user = userDAO.getUser(id);

            nama.setText(user.getNama());
            alamat.setText(user.getAlamat());
            jabatan.setText(user.getJabatan());
            noTelp.setText(user.getNoTelp());
            email.setText(user.getEmail());
            //password.setText(user.getPassword());

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
                    /*String pass = String.valueOf(password.getText());
                    String rePass = String.valueOf(rePassword.getText());
                    String newPass = String.valueOf(newPassword.getText());*/

                    if(String.valueOf(nama.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Nama pengguna belum terisi.");
                    }
                    if(String.valueOf(alamat.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Alamat pengguna belum terisi.");
                    }
                    if(String.valueOf(jabatan.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Jabatan pengguna belum terisi.");
                    }
                    if(String.valueOf(noTelp.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Nomor telepon pengguna belum terisi.");
                    }
                    if(String.valueOf(email.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Email pengguna belum terisi.");
                    }
                    if(!isValidEmail(String.valueOf(email.getText()))){
                        errorToast("Kesalahan : Format email tidak sesuai.");
                    }
                    if(String.valueOf(password.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Kata sandi pengguna belum terisi.");
                    }
                    if(!(String.valueOf(password.getText()).equalsIgnoreCase("")) && (String.valueOf(newPassword.getText()).equalsIgnoreCase("")) && !((String.valueOf(rePassword.getText()).equalsIgnoreCase("")))){
                        errorToast("Kesalahan : Kata sandi baru belum terisi.");
                    }
                    if(!(String.valueOf(password.getText()).equalsIgnoreCase("")) && (String.valueOf(newPassword.getText()).equalsIgnoreCase("")) && (String.valueOf(rePassword.getText()).equalsIgnoreCase(""))){
                        if(!(String.valueOf(password.getText()).equals(user.getPassword()))){
                            errorToast("Kesalahan : Kata sandi yang dimasukkan salah, sehingga data tidak dapat diubah.");
                        }
                        if(!(new Connection().checkConnection(getActivity())) &&  pref.getInt("kirim_user", -1) == 1) {

                            errorToast("Peringatan: Tidak ada koneksi internet.");
                        }
                        else{

                            if(new Connection().checkConnection(getActivity()) &&  pref.getInt("kirim_user", -1) == 1) {
                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("aksi", "update"));
                                //Log.d("idcek",""+id);
                                params.add(new BasicNameValuePair("id", ""+pref.getInt("id_user", -1)));
                                params.add(new BasicNameValuePair("nama", String.valueOf(nama.getText())));
                                params.add(new BasicNameValuePair("alamat", String.valueOf(alamat.getText())));
                                params.add(new BasicNameValuePair("jabatan",  String.valueOf(jabatan.getText())));
                                params.add(new BasicNameValuePair("notelp",  String.valueOf(noTelp.getText())));
                                params.add(new BasicNameValuePair("email",String.valueOf(email.getText())));
                                params.add(new BasicNameValuePair("password",  password.getText().toString()));
                                RequestData requestData = new RequestData(
                                        "userdao.php",
                                        params,
                                        getActivity(),
                                        "Mengubah User") {
                                    @Override
                                    protected void onPostExecute(JSONArray data) {
                                        pDialog.dismiss();

                                        try {
                                            Toast.makeText(
                                                    getActivity(),
                                                    data.get(0).toString(),
                                                    Toast.LENGTH_SHORT
                                            ).show();

                                            if(!data.get(0).toString().contains("Kesalahan"))
                                            {
                                                user.setNama(String.valueOf(nama.getText()));
                                                user.setAlamat(String.valueOf(alamat.getText()));
                                                user.setJabatan(String.valueOf(jabatan.getText()));
                                                user.setNoTelp(String.valueOf(noTelp.getText()));
                                                user.setEmail(String.valueOf(email.getText()));
                                                user.setPassword(String.valueOf(password.getText()));

                                                userDAO.updateUser(user);
                                                Toast.makeText(
                                                        rootView.getContext(),
                                                        "Informasi pengguna telah diubah.",
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                                userDAO.close();
                                                Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                };
                                requestData.execute();
                            }
                            else {

                                user.setNama(String.valueOf(nama.getText()));
                                user.setAlamat(String.valueOf(alamat.getText()));
                                user.setJabatan(String.valueOf(jabatan.getText()));
                                user.setNoTelp(String.valueOf(noTelp.getText()));
                                user.setEmail(String.valueOf(email.getText()));
                                user.setPassword(String.valueOf(password.getText()));

                                userDAO.updateUser(user);
                                Toast.makeText(
                                        rootView.getContext(),
                                        "Informasi pengguna telah diubah.",
                                        Toast.LENGTH_SHORT
                                ).show();
                                userDAO.close();
                                Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                    if(!(String.valueOf(password.getText()).equalsIgnoreCase("")) && !(String.valueOf(newPassword.getText()).equalsIgnoreCase("")))
                    {
                        if(!(String.valueOf(password.getText()).equals(user.getPassword()))) {
                            errorToast("Kesalahan : Kata sandi yang dimasukkan salah, sehingga data tidak dapat diubah.");
                        }
                        else {
                            if (String.valueOf(rePassword.getText()).equalsIgnoreCase("")) {
                                errorToast("Kesalahan : Konfirmasi kata sandi baru belum terisi.");
                            } else {
                                if (!(String.valueOf(newPassword.getText()).equals(String.valueOf(rePassword.getText())))) {
                                    errorToast("Kesalahan : Kata sandi dan konfirmasi kata sandi tidak sama.");
                                } else {

                                    if(new Connection().checkConnection(getActivity()) &&  pref.getInt("kirim_user", -1) == 1) {
                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("aksi", "update"));
                                        //Log.d("idcek",""+id);
                                        params.add(new BasicNameValuePair("id", ""+ pref.getInt("id_user", -1)));
                                        params.add(new BasicNameValuePair("nama", String.valueOf(nama.getText())));
                                        params.add(new BasicNameValuePair("alamat", String.valueOf(alamat.getText())));
                                        params.add(new BasicNameValuePair("jabatan",  String.valueOf(jabatan.getText())));
                                        params.add(new BasicNameValuePair("notelp",  String.valueOf(noTelp.getText())));
                                        params.add(new BasicNameValuePair("email",String.valueOf(email.getText())));
                                        params.add(new BasicNameValuePair("password",  newPassword.getText().toString()));
                                        RequestData requestData = new RequestData(
                                                "userdao.php",
                                                params,
                                                getActivity(),
                                                "Mengubah User") {
                                            @Override
                                            protected void onPostExecute(JSONArray data) {
                                                pDialog.dismiss();

                                                try {
                                                    Toast.makeText(
                                                            getActivity(),
                                                            data.get(0).toString(),
                                                            Toast.LENGTH_SHORT
                                                    ).show();

                                                    if(!data.get(0).toString().contains("Kesalahan"))
                                                    {
                                                        user.setNama(String.valueOf(nama.getText()));
                                                        user.setAlamat(String.valueOf(alamat.getText()));
                                                        user.setJabatan(String.valueOf(jabatan.getText()));
                                                        user.setNoTelp(String.valueOf(noTelp.getText()));
                                                        user.setEmail(String.valueOf(email.getText()));
                                                        user.setPassword(String.valueOf(password.getText()));

                                                        userDAO.updateUser(user);
                                                        Toast.makeText(
                                                                rootView.getContext(),
                                                                "Informasi pengguna telah diubah.",
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                        userDAO.close();
                                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                                Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }
                                        };
                                        requestData.execute();
                                    }
                                    else {


                                        user.setNama(String.valueOf(nama.getText()));
                                        user.setAlamat(String.valueOf(alamat.getText()));
                                        user.setJabatan(String.valueOf(jabatan.getText()));
                                        user.setNoTelp(String.valueOf(noTelp.getText()));
                                        user.setEmail(String.valueOf(email.getText()));
                                        user.setPassword(String.valueOf(newPassword.getText()));

                                        userDAO.updateUser(user);
                                        Toast.makeText(
                                                rootView.getContext(),
                                                "Informasi pengguna telah diubah.",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        userDAO.close();
                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                            }
                        }
                    }
                }
            });

            return rootView;
        }

    }
}
