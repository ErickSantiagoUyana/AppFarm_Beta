package com.example.kappgranja.ui.management;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kappgranja.R;

public class AnimalUpdateFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.update_animal, container, false);
        return view;
    }
/*
    private void updateAnimalList(){
        // get all data from sqlite
        Cursor cursor = ManagementFragment.sqLiteHelper.getData("SELECT * FROM "+NameTab);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String year = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Cow(name, year, image, id));
        }
        adapter.notifyDataSetChanged();
    }*/
}
