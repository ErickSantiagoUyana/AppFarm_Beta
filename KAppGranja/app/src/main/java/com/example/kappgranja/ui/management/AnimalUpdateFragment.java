package com.example.kappgranja.ui.management;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kappgranja.R;

public class AnimalUpdateFragment extends Fragment {


    private Cow Cow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal_form, container, false);

        Cow cow = (Cow) getArguments().getSerializable("P");

        cow.getAge();
        TextView textView = view.findViewById(R.id.textName);
        textView.setText(cow.getAge());
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
