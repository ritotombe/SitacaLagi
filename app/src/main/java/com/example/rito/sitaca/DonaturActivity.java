package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class DonaturActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener{

    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donatur);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donatur, menu);

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
                fragment.adapterDonatur.filter(newText);
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
                        "Kesalahan : Belum ada donatur yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" donatur yang anda pilih ingin dihapus?")
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

    /*public void slideDelete(){
        fragment.slideDeleteFragment();
    }*/

    public void showTambahDonatur (View view) {
        Intent intent = new Intent(this, TambahDonaturActivity.class);
        startActivity(intent);
    }

    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    //tambah
    protected  void onResume(){
        super.onResume();
        fragment.populateList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAnggotaDao.close();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ListView mListviewDonatur;
        private ImageButton mBtnAddDonatur;
        public View rootView;
        public DonaturListAdapter adapterDonatur;
        public DonaturDAO mDonaturDao;
        public boolean isDelete = false, onDelete=true;
        private List<Donatur> listDonatur;
        public List<Donatur> deleteList;
        public CheckBox cb;

        private boolean donaturExists(Donatur donatur) {
            int id = donatur.getId_donatur();
            int donaturCount = listDonatur.size();

            for (int i = 0; i < donaturCount; i++) {
                if (id == (int) listDonatur.get(i).getId_donatur())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }

        private class DonaturListAdapter extends ArrayAdapter<Donatur> {

            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<Donatur> arraylist;
            public List<Donatur> mListdonatur;

            public DonaturListAdapter(List<Donatur> mListDonatur) {
                super(getActivity(),
                        R.layout.list_item_donatur,
                        // R.id.listItemBukuTextview,
                        mListDonatur);
                this.mListdonatur = mListDonatur;
                this.arraylist = new ArrayList<Donatur>();
                this.arraylist.addAll(mListDonatur);
            }

            private class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView nama;
                TextView jenisDonatur;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final Donatur  currentDonatur = listDonatur.get(position);
                mDonaturDao = new DonaturDAO(getContext());

                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_donatur, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nama = (TextView) view.findViewById(R.id.viewNama);
                    viewHolder.jenisDonatur = (TextView) view.findViewById(R.id.viewJenisDonatur);
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
                            if(!deleteList.contains(currentDonatur))
                                deleteList.add(currentDonatur);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else {
                            deleteList.remove(currentDonatur);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });



                viewHolder.nama.setText(currentDonatur.getNama());
                viewHolder.jenisDonatur.setText(currentDonatur.getJenisDonatur());
                ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                if(deleteList.contains(currentDonatur))
                    viewHolder.cb.setChecked(true);
                if(!deleteList.contains(currentDonatur))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
            return view;
            }
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListdonatur.clear();
                if (charText.length() == 0) {
                    mListdonatur.addAll(arraylist);
                }
                else
                {
                    for (Donatur donatur : arraylist)
                    {
                        if (mDonaturDao.getDonatur(donatur.getId_donatur()).getNama().toLowerCase(Locale.getDefault()).contains(charText))
                        {
                            mListdonatur.add(donatur);
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
                    mDonaturDao.deleteDonatur(deleteList.get(i));
                }
            }
                Toast.makeText(
                        rootView.getContext(),
                        deleteList.size()+ " donatur telah dihapus.",
                        Toast.LENGTH_SHORT
                ).show();
            deleteList = new ArrayList<>();
            populateList();
        }

        public void showTambahDonatur(View view) {
            Intent intent = new Intent(getActivity(), TambahDonaturActivity.class);
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
            mListviewDonatur = (ListView) rootView.findViewById(R.id.listViewDonatur);
            listDonatur = mDonaturDao.getAllDonatur();
            listDonatur.remove(0);
            Collections.reverse(listDonatur);
            adapterDonatur = new DonaturListAdapter(listDonatur);
            mListviewDonatur.setAdapter(adapterDonatur);
            mListviewDonatur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahDonaturActivity.class);
                    intent.putExtra("id", listDonatur.get(position).getId_donatur());
                    mDonaturDao.close();
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_donatur, container, false);

            mBtnAddDonatur = (ImageButton) rootView.findViewById(R.id.add_button);
            mDonaturDao = new DonaturDAO(rootView.getContext());
            deleteList = new ArrayList<Donatur>();


            mBtnAddDonatur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahDonaturActivity.class);
                    startActivity(intent);
                  getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }

    }
}

