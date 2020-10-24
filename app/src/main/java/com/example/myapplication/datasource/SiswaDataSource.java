package com.example.myapplication.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.content.res.FontResourcesParserCompat;

import com.example.myapplication.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class SiswaDataSource {

 private final DatabaseHelper databaseHelper;

 public SiswaDataSource(DatabaseHelper databaseHelper) {this.databaseHelper = databaseHelper ;}

private ContentValues convertToContentvalues(Siswa siswa) {
    ContentValues contentValues = new ContentValues();
        contentValues.put("id", siswa.getId());
        contentValues.put("namaDepan" , siswa.getNamaDepan());
        contentValues.put("namaBelakang" , siswa.getNamaBelakang());
        contentValues.put("phoneNumber", siswa.getPhoneNumber());
        contentValues.put("education", siswa.getEducation());
        contentValues.put("email", siswa.getEmail());
        contentValues.put("gender" , siswa.getGender());
        contentValues.put("hoby", siswa.getHoby());
        contentValues.put("alamat", siswa.getAlamat());
        contentValues.put("tglLahir", siswa.getTanggallahir());
        return contentValues;
}

private Siswa convertToSiswa (Cursor cursor){
    Siswa siswa = new Siswa();
    siswa.setId(cursor.getLong(0));
    siswa.setNamaDepan(cursor.getString(1));
    siswa.setNamaBelakang(cursor.getString(2));
    siswa.setPhoneNumber(cursor.getString(3));
    siswa.setEmail(cursor.getString(4));
    siswa.setTanggallahir(cursor.getString(5));
    siswa.setGender(cursor.getString(6));
    siswa.setEducation(cursor.getString(7));
    siswa.setHoby(cursor.getString(8));
    siswa.setAlamat(cursor.getString(9));


    return siswa ;
}

public void save ( Siswa siswa){
    ContentValues contentValues = convertToContentvalues(siswa);

    SQLiteDatabase database = databaseHelper.getWritableDatabase();
    database.insert("siswa", null, contentValues);
    database.close();
}



public List<Siswa> getAll() {

     SQLiteDatabase database = databaseHelper.getReadableDatabase();
     Cursor cursor = database.rawQuery("SELECT *FROM siswa", null);

     List<Siswa> foundSiswaList = new ArrayList<>();

     while (cursor.moveToNext()){
            Siswa siswa = convertToSiswa(cursor);
            foundSiswaList.add(siswa);
     }
    cursor.close();
     database.close();

     return foundSiswaList;
}

public Siswa findById(Long id){
     SQLiteDatabase database = databaseHelper.getReadableDatabase();
     Cursor cursor = database.rawQuery("SELECT *FROM siswa WHERE id=?"
     , new String[]{String.valueOf(id)}
     );

     boolean found = cursor.getCount() > 0 ;


    if(found){
        cursor.moveToNext();
        Siswa siswa = convertToSiswa(cursor);

        cursor.close();
        database.close();

        return siswa;
    } else {
        throw new RuntimeException("Data siswa dengan id" + id + "tidak di temukan");
    }

}

//    public List<Siswa> search (String keyword){
//     List<Siswa> siswas = new ArrayList<>();
//     String sql = "SELECT *FROM siswa WHERE" + "namaDepan LIKE ? OR namaBelakang LIKE?";
//
//    SQLiteDatabase database = databaseHelper.getReadableDatabase();
//            Cursor cursor = database.rawQuery(sql, new String[]{"%","%" + keyword + "%"});
//            cursor.moveToFirst();
//
//            while (!cursor.isAfterLast()){
//
//                siswas.add(siswa);
//
//                cursor.moveToNext();
//            }
//
//            return siswas;
//    }

    public void removeSiswa (Siswa siswa) {
            SQLiteDatabase database =databaseHelper.getWritableDatabase();
            int id = database.delete("siswa", "id=?",
                        new String[]{Long.toString(siswa.getId())});
    }


    public void update (Siswa siswa){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = convertToContentvalues(siswa);
        database.update("siswa" , contentValues, "id=?" , new String[]{
                        String.valueOf(siswa.getId())
        });
        database.close();
    }



}
