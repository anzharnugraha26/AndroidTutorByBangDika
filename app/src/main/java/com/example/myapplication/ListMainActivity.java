package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.adapter.SiswaItemAdapter;
import com.example.myapplication.datasource.DatabaseHelper;
import com.example.myapplication.datasource.SiswaDataSource;
import com.example.myapplication.model.Siswa;


import java.util.ArrayList;
import java.util.List;

public class ListMainActivity extends AppCompatActivity {

    private  ListView siswaLv ;
    private SiswaItemAdapter adapter;

    private void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void starFormDetailActivity(int position) {
        Intent intent = new Intent(this, MainActivity.class);

        Siswa selectedSiswa = adapter.getItem(position);
        intent.putExtra("id_siswa", selectedSiswa.getId());

        startActivity(intent);
    }



    private void starDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);

        Siswa selectedSiswa = adapter.getItem(position);
        intent.putExtra("id_siswa", selectedSiswa.getId());

        startActivity(intent);
    }



    private void loadDataSiswa(){
        try{
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource siswaDataSource = new SiswaDataSource(databaseHelper);
                List<Siswa> foundSiswaList = siswaDataSource.getAll();
                 adapter = new SiswaItemAdapter(this, foundSiswaList);
//
//                List<String> siswaNames =new ArrayList<>();
//                for (Siswa siswa : foundSiswaList){
//                        siswaNames.add(siswa.getNamaDepan() + "" + siswa.getNamaBelakang());
//                }
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, siswaNames);
                siswaLv.setAdapter(adapter);

                siswaLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        starDetailActivity(position);
                    }
                });


                showToast("Data Loaded Succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Failed to load data caused by" + e.getMessage());
        }
    }

    private void startFormActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

//    private void deleteSiswa (Siswa siswa){
//        SiswaDataSource.open();
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();

        if (selectedMenuId == R.id.AddSiswaMenu){
            startFormActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected( MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedPosition = info.position;

        switch (id){
            case R.id.action_delete:
                break;
            case R.id.action_edit:
                starFormDetailActivity(selectedPosition);
                break;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        siswaLv = findViewById(R.id.siswaLv);

        registerForContextMenu(siswaLv);

//        loadDataSiswa();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataSiswa();
    }
}
