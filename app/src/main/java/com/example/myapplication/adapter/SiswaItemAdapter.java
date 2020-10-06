package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Siswa;

import java.util.List;

public class SiswaItemAdapter extends ArrayAdapter<Siswa> {

    public SiswaItemAdapter(Context context, List<Siswa> objects){
        super(context, R.layout.item_siswa , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listitemView = convertView;

        if (listitemView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listitemView = inflater.inflate(R.layout.item_siswa, parent, false);
        }
        TextView etNamaDepan = listitemView.findViewById(R.id.etNamaDepan);
        TextView genderRb = listitemView.findViewById(R.id.genderRb);
        TextView ListItem = listitemView.findViewById(R.id.listItem);
        TextView etNoHandphone = listitemView.findViewById(R.id.etNoHandphone);
        TextView ettgllahir = listitemView.findViewById(R.id.etTgllahir);

        Siswa siswa = getItem(position);
        etNamaDepan.setText(siswa.getNamaDepan() + " " + siswa.getNamaBelakang());
        genderRb.setText(siswa.getGender());
        etNoHandphone.setText(siswa.getPhoneNumber());
        ListItem.setText(siswa.getEducation());
        ettgllahir.setText("Tanggal Lahir : " + siswa.getTanggallahir());

        return listitemView;
    }


}
