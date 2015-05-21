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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


public class TbSekitarActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener{

    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_sekitar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tb_sekitar, menu);
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
                fragment.adapterTbSekitar.filter(newText);
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
            if(id == R.id.delete)
            {
                if(fragment.deleteList!= null && fragment.deleteList.size() == 0)
                {
                    Toast.makeText(
                            getApplicationContext(),
                            "Kesalahan : Belum ada taman baca yang dipilih.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("Konfirmasi")
                            .setMessage("Apakah anda yakin menghapus "+fragment.deleteList.size()+" taman baca yang anda pilih ?")
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void slideDelete(){
        fragment.slideDeleteFragment();
    }

    public void showTambahTbSekitar(View view) {
        Intent intent = new Intent(this, TambahTbSekitarActivity.class);
        startActivity(intent);
    }

    @Override
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
    protected void onDestroy() {
        super.onDestroy();
        //mPeminjamanDao.close();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public ListView mListviewTbSekitar;
        public ImageButton mBtnAddTbSekitar;
        public View rootView;
        public TbSekitarListAdapter adapterTbSekitar;
        public List<TbSekitar> listTbSekitar;
        public TbSekitarDAO mTbSekitarDao;
        public boolean isDelete = false, onDelete=true;
        public CheckBox cb;
        public List<TbSekitar> deleteList;
        public PertukaranBukuDAO pertukaranBukuDao;

        private void initViews(){
            //this.mListviewLogHarian.setOnItemClickListener(this);
            // this.mListviewLogHarian.setOnItemLongClickListener(this);
            //this.mBtnAddLogHarian.setOnClickListener(this);
        }

        private boolean tbSekitarExists(TbSekitar tbSekitar) {
            int id = tbSekitar.getId_tb_sekitar();
            int tbSekitarCount = listTbSekitar.size();

            for (int i = 0; i < tbSekitarCount; i++) {
                if (id == (int) listTbSekitar.get(i).getId_tb_sekitar())
                    return true;
            }
            return false;
        }

        public PlaceholderFragment() {
        }

        private class TbSekitarListAdapter extends ArrayAdapter<TbSekitar> {
            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<TbSekitar> arraylist;
            public List<TbSekitar> mListtbsekitar;

            public TbSekitarListAdapter(List<TbSekitar> mListTbSekitar) {
                super(getActivity(),
                        R.layout.list_item_tbsekitar,
                        // R.id.listItemPeminjamanTextview,
                        mListTbSekitar);
                this.mListtbsekitar = mListTbSekitar;
                this.arraylist = new ArrayList<TbSekitar>();
                this.arraylist.addAll(mListTbSekitar);
            }

            private class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView nama;
                TextView noTelp;
                CheckBox cb;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;
                final TbSekitar currentTbSekitar = listTbSekitar.get(position);

                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_tbsekitar, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nama = (TextView) view.findViewById(R.id.viewNama);
                    viewHolder.noTelp = (TextView) view.findViewById(R.id.viewNoTelp);
                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                }
                else
                {
                    viewHolder = (ViewHolder) view.getTag();
                }

                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(!deleteList.contains(currentTbSekitar))
                                deleteList.add(currentTbSekitar);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else {
                            deleteList.remove(currentTbSekitar);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });

                //Simpan Datanya ke Holder
                viewHolder.nama.setText(currentTbSekitar.getNama());
                viewHolder.noTelp.setText(String.valueOf(currentTbSekitar.getNo_telepon()));
                ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                if(deleteList.contains(currentTbSekitar))
                    viewHolder.cb.setChecked(true);
                if(!deleteList.contains(currentTbSekitar))
                    viewHolder.cb.setChecked(false);

                view.setTag(viewHolder);
                return view;
            }
            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListtbsekitar.clear();
                if (charText.length() == 0) {
                    mListtbsekitar.addAll(arraylist);
                }
                else
                {
                    for (TbSekitar tbSekitar : arraylist)
                    {
                        if (mTbSekitarDao.getTbSekitar(tbSekitar.getId_tb_sekitar()).getNama().toLowerCase(Locale.getDefault()).contains(charText))
                        {
                            mListtbsekitar.add(tbSekitar);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }


        public void delete()
        {int cnt = 0;
            if(deleteList.size()!=0)
            {

                for(int i =0; i<deleteList.size();i++)
                {
                    TbSekitar tbSekitar = deleteList.get(i);
                    if(pertukaranBukuDao.isMeminjam(tbSekitar.getId_tb_sekitar())){
                        cnt++;
                    }
                    else
                        mTbSekitarDao.deleteTbSekitar(deleteList.get(i));
                }
            }
            if(cnt>0)
            {
                Toast.makeText(
                        rootView.getContext(),
                        "Peringatan : "+cnt+ " taman baca tidak dapat dihapus karena sedang meminjam buku.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            if(deleteList.size()>0) {
                if(deleteList.size() - cnt > 0) {
                    Toast.makeText(
                            rootView.getContext(),
                            "" + (deleteList.size() - cnt) + " taman baca telah dihapus.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
            deleteList = new ArrayList<>();
            populateList();
        }

        public void showTambahTbSekitar(View view) {
            Intent intent = new Intent(getActivity(), TambahTbSekitarActivity.class);
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
            mListviewTbSekitar = (ListView) rootView.findViewById(R.id.listViewTbSekitar);
            listTbSekitar = mTbSekitarDao.getAllTbSekitar();
            listTbSekitar.remove(0);
            Collections.reverse(listTbSekitar);
            cb = (CheckBox) rootView.findViewById(R.id.delCb);
            adapterTbSekitar = new TbSekitarListAdapter(listTbSekitar);
            mListviewTbSekitar.setAdapter(adapterTbSekitar);
            mListviewTbSekitar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent = new Intent(rootView.getContext(), UbahTbSekitarActivity.class);
                    intent.putExtra("id", listTbSekitar.get(position).getId_tb_sekitar());
                    mTbSekitarDao.close();
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (rootView == null)
                rootView = inflater.inflate(R.layout.fragment_tb_sekitar, container, false);

            mBtnAddTbSekitar = (ImageButton) rootView.findViewById(R.id.add_button);
            mTbSekitarDao = new TbSekitarDAO(rootView.getContext());
            pertukaranBukuDao = new PertukaranBukuDAO(rootView.getContext());
            deleteList = new ArrayList<TbSekitar>();

            mBtnAddTbSekitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), TambahTbSekitarActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            populateList();
            return rootView;
        }
    }
}

