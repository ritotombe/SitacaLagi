package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LogHarianActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    PlaceholderFragment fragment = new PlaceholderFragment();

    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_harian);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_harian, menu);

        ////////////////////////////ubah search!!!!!!!!!!!!!!!!!!!!!!!
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        int searchIconId = searchView.getContext().getResources().
                getIdentifier("android:id/search_button", null, null);
        ImageView searchIcon = (ImageView) searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.mipmap.ic_search);
        searchIcon.setScaleX(0.7f);
        searchIcon.setScaleY(0.7f);
        //  searchView.setAnimation(new TranslateAnimation(0,100,0,0));
        //searchView.startAnimation();
        searchView.setIconifiedByDefault(true);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        // Getting the 'search_plate' LinearLayout.
        LinearLayout searchPlate = (LinearLayout) searchView.findViewById(searchPlateId);
        // Setting background of 'search_plate' to earlier defined drawable.
        searchPlate.setBackgroundResource(R.drawable.search);
        searchPlate.setLayoutTransition(new LayoutTransition());


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                fragment.adapterLogHarian.filter(newText);
                System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
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
        if (id == R.id.search)
        {
            ////////////////////////////ubah search/ada yang dihapus disini!!!!!!!!!!!!!!!!!!!!!!!
            return true;
        }
        if(id == R.id.delete)
        {
            if(fragment.deleteList!= null && fragment.deleteList.size() == 0)
            {
                Toast.makeText(
                        getApplicationContext(),
                        "Kesalahan : Belum ada log harian yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" log harian yang anda pilih ingin dihapus?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                fragment.delete();
                            }
                        })
                        .setNegativeButton("Tidak", null).show();
                //slideDelete();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void slideDelete(){
        fragment.slideDeleteFragment();
    }

    public void showTambahLogHarian(View view) {
        Intent intent = new Intent(this, TambahLogHarianActivity.class);
        startActivity(intent);
    }

    //tambah
    protected  void onResume(){
        super.onResume();
        fragment.populateList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mPeminjamanDao.close();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public ListView mListviewLogHarian;
        public ImageButton mBtnAddLogHarian;
        public View rootView;
        public LogHarianListAdapter adapterLogHarian;
        public List<LogHarian> listLogHarian;
        public LogHarianDAO mLogHarianDao;
        public boolean isDelete = false, onDelete=true;
        public CheckBox cb;
        public List<LogHarian> deleteList;

        private boolean logHarianExists(LogHarian logHarian) {
            int id = logHarian.getId_logHarian();
            int logHarianCount = listLogHarian.size();

            for (int i = 0; i < logHarianCount; i++) {
                if (id == (int) listLogHarian.get(i).getId_logHarian())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }

        private class LogHarianListAdapter extends ArrayAdapter<LogHarian> {

            public ArrayList<LogHarian> arraylist;
            public List<LogHarian> mListlogharian;

            public LogHarianListAdapter(List<LogHarian> mListLogHarian) {
                super ( getActivity(),
                        R.layout.list_item_logharian,
                        // R.id.listItemBukuTextview,
                        mListLogHarian);
                this.mListlogharian = mListLogHarian;
                this.arraylist = new ArrayList<LogHarian>();
                this.arraylist.addAll(mListLogHarian);
            }

            private class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView tanggal;
                TextView jumlahKehadiran;
                TextView realisasiJamBuka;
                TextView realisasiJamTutup;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                ViewHolder viewHolder = null;// Tambahin Holder

                final LogHarian currentLogHarian = listLogHarian.get(position);
                mLogHarianDao = new LogHarianDAO(getContext());

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_logharian, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.tanggal = (TextView) view.findViewById(R.id.viewTanggal);
                    viewHolder.jumlahKehadiran = (TextView) view.findViewById(R.id.viewJumlahKehadiran);
                    viewHolder.realisasiJamBuka = (TextView) view.findViewById(R.id.viewRealisasiJamBuka);
                    viewHolder.realisasiJamTutup = (TextView) view.findViewById(R.id.viewRealisasiJamTutup);
                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                }
                else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }

                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(!deleteList.contains(currentLogHarian))
                                deleteList.add(currentLogHarian);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else {
                            deleteList.remove(currentLogHarian);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });

                viewHolder.tanggal.setText(currentLogHarian.getTanggal());
                viewHolder.jumlahKehadiran.setText(""+currentLogHarian.getJumlah_kehadiran());
                viewHolder.realisasiJamBuka.setText(currentLogHarian.getRealisasi_jamBuka());
                viewHolder.realisasiJamTutup.setText(currentLogHarian.getRealisasi_jamTutup());
                ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                if(deleteList.contains(currentLogHarian))
                    viewHolder.cb.setChecked(true);
                if(!deleteList.contains(currentLogHarian))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
                return view;
            }
            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListlogharian.clear();
                if (charText.length() == 0) {
                    mListlogharian.addAll(arraylist);
                }
                else
                {
                    for (LogHarian logHarian : arraylist)
                    {
                        if (mLogHarianDao.getLogHarian(logHarian.getId_logHarian()).getTanggal().toLowerCase(Locale.getDefault()).contains(charText))
                        {
                            mListlogharian.add(logHarian);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void delete()
        {
            if(deleteList.size()!=0)
            {
                for(int i =0; i<deleteList.size();i++)
                {
                    mLogHarianDao.deleteLogHarian(deleteList.get(i));
                }
            }
            Toast.makeText(
                    rootView.getContext(),
                    deleteList.size()+ " log harian telah dihapus.",
                    Toast.LENGTH_SHORT
            ).show();
            deleteList = new ArrayList<>();
            populateList();
        }

        public void showTambahLogHarian(View view) {
            Intent intent = new Intent(getActivity(), TambahLogHarianActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        //Animasi saat tekan tombol delete
        public void slideDeleteFragment()
        {
            if(!isDelete) {
                isDelete = true;
                onDelete = false;
                populateList();
            }
            else{
                isDelete = false;
                onDelete = true;
                populateList();
            }
        }

        public void populateList()
        {
            mListviewLogHarian = (ListView) rootView.findViewById(R.id.listViewLogHarian);
            listLogHarian = mLogHarianDao.getAllLogHarian();
            Collections.reverse(listLogHarian);
            adapterLogHarian = new LogHarianListAdapter(listLogHarian);
            mListviewLogHarian.setAdapter(adapterLogHarian);
            mListviewLogHarian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahLogHarianActivity.class);
                    intent.putExtra("id", listLogHarian.get(position).getId_logHarian());
                    mLogHarianDao.close();
                    startActivity(intent);
                    //getActivity().finish();
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_log_harian, container, false);

            mBtnAddLogHarian = (ImageButton) rootView.findViewById(R.id.add_button);
            mLogHarianDao = new LogHarianDAO(rootView.getContext());
            deleteList = new ArrayList<LogHarian>();

            mBtnAddLogHarian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahLogHarianActivity.class);
                    startActivity(intent);
                    //getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }
    }
}
