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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class UserActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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

    public void showBuatUser (View view) {
        Intent intent = new Intent(this, BuatUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mBukuDao.close();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ImageButton mBtnAddUser;
        private UserDAO mUserDao;
        private View rootView;

        private List<User> listUser;

        private boolean userExists(User user) {
            int id = user.getId_user();
            int userCount = listUser.size();

            for (int i = 0; i < userCount; i++) {
                if (id == (int) listUser.get(i).getId_user())
                    return true;
            }
            return false;
        }

        public void showBuatUser(View view) {
            Intent intent = new Intent(getActivity(), BuatAnggotaActivity.class);
            startActivity(intent);
        }

        public PlaceholderFragment() {
        }

            public View getView(int position, View view, ViewGroup parent) {
                if (view == null)
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_user, parent, false);
                final View a = view;

                final User currentUser = listUser.get(position);
                mUserDao = new UserDAO(view.getContext());

                String namaUser = mUserDao.getUser(currentUser.getId_user()).getNama();
                String jabatanUser = mUserDao.getUser(currentUser.getId_user()).getJabatan();
                String noTelpUser = mUserDao.getUser(currentUser.getId_user()).getNoTelp();

                TextView viewNamaUser = (TextView) view.findViewById(R.id.viewNamaUser);
                viewNamaUser.setText(namaUser);
                TextView viewJabatan = (TextView) view.findViewById(R.id.viewJabatan);
                viewJabatan.setText(jabatanUser);
                TextView viewNoTelp = (TextView) view.findViewById(R.id.viewNoTelp);
                viewNoTelp.setText(noTelpUser);

                return view;
            }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_user, container, false);

            mBtnAddUser = (ImageButton) rootView.findViewById(R.id.add_button);
            mUserDao = new UserDAO(rootView.getContext());

            mBtnAddUser.setOnClickListener(new View.OnClickListener() { //-------------------------------------------------------->>Baru
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), BuatUserActivity.class);
                    startActivity(intent);
                }
            });
            return rootView;
        }
    }
}
