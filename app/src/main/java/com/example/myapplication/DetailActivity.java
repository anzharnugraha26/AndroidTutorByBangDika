package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.datasource.DatabaseHelper;
import com.example.myapplication.datasource.SiswaDataSource;
import com.example.myapplication.model.Siswa;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView fullNameTv  ;
    TextView noHp ;
    TextView gender;
    TextView jenjang;
    TextView hobi ;
    TextView alamat ;

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void loadDetailDataSiswa(long idSiswa){
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
            Siswa siswa = dataSource.findById(idSiswa);

            String fullname = siswa.getNamaDepan() + " " + siswa.getNamaBelakang();
            fullNameTv.setText(fullname);


            noHp.setText(siswa.getPhoneNumber());

            gender.setText(siswa.getGender());

            jenjang.setText(siswa.getGender());

            hobi.setText(siswa.getHoby());


            alamat.setText(siswa.getAlamat());

            showToast("Data Siswa Berhasil");
        } catch (Exception e){
            showToast(e.getMessage());
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();

        if(selectedMenuId == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameTv = findViewById(R.id.etNama);
        noHp = findViewById(R.id.etNoHandphone);
        gender = findViewById(R.id.genderRb);
        jenjang = findViewById(R.id.jenjang);
        hobi = findViewById(R.id.ethobi);
        alamat = findViewById(R.id.etAlamat);

        long receivedSiswa = getIntent().getLongExtra("id_siswa", -1);


        if (receivedSiswa == -1){
            showToast("Tidak menerima data siswa");
        } else {
            loadDetailDataSiswa(receivedSiswa);;
        }

    }

}
