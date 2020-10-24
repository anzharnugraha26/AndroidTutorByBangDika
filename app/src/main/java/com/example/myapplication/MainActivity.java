package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.myapplication.datasource.DatabaseHelper;
import com.example.myapplication.datasource.SiswaDataSource;
import com.example.myapplication.model.Siswa;
import com.google.android.material.button.MaterialButton;

//

//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etNamaDepan;
    private EditText etNamaBelakang;
    private EditText etNoHandphone;
    private EditText etEmail;
    private EditText tglLahirEt;
    private RadioGroup genderRb;
    private Spinner educationSp;
    private CheckBox cbMembaca;
    private CheckBox cbMenulis;
    private CheckBox cbMenggambar;
    private EditText etAlamat;
    private Button btnUpdate;


    private Siswa receivedSiswa;


    private void showToast(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadDetailDataSiswa(long idSiswa){
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
            receivedSiswa = dataSource.findById(idSiswa);

            etNamaDepan.setText(receivedSiswa.getNamaDepan());
            etNamaBelakang.setText(receivedSiswa.getNamaBelakang());
            etNoHandphone.setText(receivedSiswa.getPhoneNumber());
            etAlamat.setText(receivedSiswa.getAlamat());
            etEmail.setText(receivedSiswa.getEmail());
            tglLahirEt.setText(receivedSiswa.getTanggallahir());

            String gender = receivedSiswa.getGender();
            int selectedId = (gender.equals("Pria")) ? R.id.priaRb : R.id.wanitaRb;
            genderRb.check(selectedId);

            String hobi = receivedSiswa.getHoby();
            cbMembaca.setChecked(hobi.contains("Membaca"));
            cbMenulis.setChecked(hobi.contains("Menulis"));
            cbMenggambar.setChecked(hobi.contains("Menggambar"));

            SpinnerAdapter adapter = educationSp.getAdapter();
            if (adapter instanceof ArrayAdapter){
                int position =  ((ArrayAdapter) adapter).getPosition(receivedSiswa.getEducation());
                educationSp.setSelection(position);
            }

            btnUpdate.setText("Update");



            showToast("Data Siswa Berhasil");
        } catch (Exception e){
            showToast(e.getMessage());
        }
    }


    private void initDataSiswa(Siswa siswa){
        String inputNamaDepan = etNamaDepan.getText().toString().trim();
        String inputNamaBelakang = etNamaBelakang.getText().toString().trim();
        String inputNoHandphone = etNoHandphone.getText().toString().trim();
        String inputEmail = etEmail.getText().toString().trim();
        String inputAlamat = etAlamat.getText().toString().trim();
        String inputTgllahir = tglLahirEt.getText().toString().trim();

        String selectedGender;
        if (genderRb.getCheckedRadioButtonId()== R.id.priaRb) {
            selectedGender = "Pria" ;
        } else{
            selectedGender = "wanita" ;
        }

        List<String> selectedHobies = new ArrayList<>();
        if ( cbMembaca.isChecked()) {
            selectedHobies.add("Membaca");
        }
        if ( cbMenulis.isChecked()) {
            selectedHobies.add("Menulis");
        }
        if ( cbMenggambar.isChecked()) {
            selectedHobies.add("Membaca");
        }
        String joinHobi = TextUtils.join(",", selectedHobies);

        String selectEducation = educationSp.getSelectedItem().toString();


        siswa.setNamaDepan(inputNamaDepan);
        siswa.setNamaBelakang(inputNamaBelakang);
        siswa.setPhoneNumber(inputNoHandphone);
        siswa.setEmail(inputEmail);
        siswa.setEducation(selectEducation);
        siswa.setGender(selectedGender);
        siswa.setTanggallahir(inputTgllahir);
        siswa.setHoby(joinHobi);
        siswa.setAlamat(inputAlamat);

    }

    private void addNewSiswa(){
        Siswa siswa = new Siswa();
        initDataSiswa(siswa);
        try{
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource dataSource = new SiswaDataSource(databaseHelper);
            dataSource.save(siswa);
            showToast("Sukses");
            finish();
        } catch (Exception e) {
            showToast(e.getMessage());
        }

    }

    private void updateSiswa(){
        initDataSiswa(receivedSiswa);

        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDataSource datasource = new SiswaDataSource(databaseHelper);
            datasource.update(receivedSiswa);
            showToast("SUKSES");
//            Agar automatic ke form
            finish();
        } catch (Exception e) {
            showToast(e.getMessage());
        }

    }


    private void save(){
        if (receivedSiswa != null) {
            updateSiswa();
        } else {
            addNewSiswa();
        }

//        showToast("Hi, " + siswa.getNamaDepan() + "" + siswa.getNamaBelakang() + "\n"
//            +"Phone Number :" + siswa.getPhoneNumber() + "\n" +
//                "Gender :" + siswa.getGender() + "\n" +
//                    "Email:" + siswa.getEmail() + "\n" +
//                    "Education : " + siswa.getEducation() + "\n" +
//                    "Hobi :" + siswa.getHoby() + "\n" +
//                        "Alamat :" + siswa.getAlamat()
//        );

    }


    // belum
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
        setContentView(R.layout.activity_main);

        //muncul tombol back

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNamaDepan = findViewById(R.id.etNamaDepan);
        etNamaBelakang = findViewById(R.id.etNamaBelakang);
        etNoHandphone = findViewById(R.id.etNoHandphone);
        etEmail = findViewById(R.id.etEmail);
        tglLahirEt = findViewById(R.id.tglLahirEt);
        genderRb =findViewById(R.id.genderRb);
        educationSp = findViewById(R.id.listItem);
        cbMembaca = findViewById(R.id.cbMembaca);
        cbMenulis = findViewById(R.id.cbMenulis);
        cbMenggambar = findViewById(R.id.cbMenggambar);
        etAlamat = findViewById(R.id.etAlamat);
        btnUpdate = findViewById(R.id.saveBtn);

           Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        tglLahirEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        long receivedSiswa = getIntent().getLongExtra("id_siswa", -1);


        if (receivedSiswa == -1){
            showToast("Tidak menerima data siswa");
        } else {
            loadDetailDataSiswa(receivedSiswa);;
        }

        long receivedIdSiswa = getIntent().getLongExtra("id siswa", -1);
        if(receivedIdSiswa == -1){
                    showToast("Tidak Menerima Data Siswa");
        } else {
            loadDetailDataSiswa(receivedIdSiswa);
        }



    }
    private void showDatePickerDialog(){
        Calendar today = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = String.format(
                "%s-%s-%s", dayOfMonth, (month+1), year
        );
        tglLahirEt.setText(selectedDate);
    }
}
