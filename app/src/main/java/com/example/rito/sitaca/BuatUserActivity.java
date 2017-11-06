package com.example.rito.sitaca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class  BuatUserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_user);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("kirim", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("kirim_tb", 0);
        editor.putInt("kirim_user", 0);
        editor.commit();

        setContentView(R.layout.activity_buat_user);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buat_user, menu);
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

        private UserDAO userDAO;
        private EditText nama, alamat, jabatan, noTelp, email, password, rePassword;
        private ImageView indikator;
        private int id_pengguna;
        private View rootView;
        private ProgressDialog pDialog;

        // JSON parser class
        JSONParser jsonParser = new JSONParser();
        //php register script

        //localhost :
        //testing on your device
        //put your local ip instead,  on windows, run CMD > ipconfig
        //or in mac's terminal type ifconfig and look for the ip under en0 or en1
        // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/webservice/register.php";

        //testing on Emulator:
        private final String REGISTER_URL = "http://ritotom.be/sitacaapi/buatuser.php";

        //testing from a real server:
        //private static final String REGISTER_URL = "http://www.mybringback.com/webservice/register.php";

        //ids
        private final String TAG_SUCCESS = "success";
        private final String TAG_MESSAGE = "message";
        private final String TAG_ID = "id";

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

        public final static boolean isValidEmail(CharSequence target) {
            if (target == null)
                return false;

            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }



        public void connectionIndicator(){
            if(new Connection().checkConnection(rootView.getContext()))
            {
                indikator.setImageResource(R.drawable.online);
            }
            else
                indikator.setImageResource(R.drawable.offline);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_buat_user, container, false);


            //Cuman setting layout
            nama = (EditText) rootView.findViewById(R.id.buatUserNamaLengkap);
            alamat = (EditText) rootView.findViewById(R.id.buatUserAlamat);
            jabatan = (EditText) rootView.findViewById(R.id.buatUserJabatan);
            noTelp = (EditText) rootView.findViewById(R.id.buatUserNoTelp);
            email = (EditText) rootView.findViewById(R.id.buatUserEmail);
            password = (EditText) rootView.findViewById(R.id.buatUserKataSandi);
            rePassword = (EditText) rootView.findViewById(R.id.buatUserKonfirmasiKataSandi);
            indikator = (ImageView) rootView.findViewById(R.id.indikator);

            userDAO = new UserDAO(rootView.getContext());

            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    connectionIndicator();
                }
            }, 1);

            final Button addBtn = (Button) rootView.findViewById(R.id.button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Lihat model dan Db handlernya ada method-methodnya untuk di sini

                    boolean goodToGo = true;

                    String pass = String.valueOf(password.getText());
                    String rePass = String.valueOf(rePassword.getText());

                    if(String.valueOf(nama.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Nama pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(String.valueOf(alamat.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Alamat pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(String.valueOf(jabatan.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Jabatan pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(String.valueOf(noTelp.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Nomor telepon pengguna belum terisi.");
                    }
                    if(String.valueOf(email.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Email pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(!isValidEmail(String.valueOf(email.getText()))){
                        errorToast("Kesalahan : Format email tidak sesuai.");
                        goodToGo = false;
                    }
                    if(String.valueOf(password.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Kata sandi pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(String.valueOf(rePassword.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Konfirmasi kata sandi pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(!(pass.equalsIgnoreCase("")) && !(rePass.equalsIgnoreCase("")))
                    {
                        if(!(pass.equals(rePass))){
                        errorToast("Kesalahan : Kata sandi dan konfirmasi kata sandi tidak sama.");
                            goodToGo = false;
                        }
                        if(goodToGo){


                            userDAO.createUser(String.valueOf(nama.getText()),
                                    String.valueOf(alamat.getText()),
                                    String.valueOf(jabatan.getText()),
                                    String.valueOf(noTelp.getText()),
                                    String.valueOf(email.getText()),
                                    String.valueOf(password.getText()));

                           Toast.makeText(
                                  rootView.getContext(),
                                    "Pengguna telah dibuat.",
                                   Toast.LENGTH_SHORT
                            ).show();

                            Intent intent = new Intent(rootView.getContext(), BuatTamanBacaActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                }
            });

            return rootView;
        }
    }
}
//