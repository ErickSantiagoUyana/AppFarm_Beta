package com.example.kappgranja.ui.management;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.kappgranja.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import kotlin.jvm.internal.Ref;

public class AnimalUpdateFragment extends Fragment implements View.OnClickListener{


    private EditText edtName, edtNumber,edtAge,edtState, edtHealth, edtSex,edtRace;
    private int idUp;
    private String nameUp;
    private String numberUp;
    private String ageUp;
    private String stateUp;
    private String healthUp;
    private String SexUp;
    private String RaceUp;
    private byte[] imageViewAnimal;
    private SQLiteHelper sqLiteHelper;
    private int REQUEST_CODE_GALLERY = 888;
    private  ImageView imageView;
    private View viewAux;
    private String NameTab;


    private Button add;
    private Button cancel;
    private Button choose;

    private Cow cow;
    private Pig pig;
    private Goat goat;
    private Sheep sheep;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal_form, container, false);

        sqLiteHelper = ManagementFragment.sqLiteHelper;
        nameUp = getArguments().getString("C");

        if(getArguments().getString("C") == "COWS") {

           cow = (Cow) getArguments().getSerializable("C");
           form_dates(cow.getName(),cow.getIdNumber(),cow.getAge(),cow.getState(),cow.getHealth()
           ,cow.getSex(),cow.getRace(),cow.getImage(),cow.getId());


        }
        if(getArguments().getString("C") == "PIGS") {
             pig = (Pig) getArguments().getSerializable("C");
            form_dates(pig.getName(),pig.getIdNumber(),pig.getAge(),pig.getState(),pig.getHealth()
                    ,pig.getSex(),pig.getRace(),pig.getImage(),pig.getId());

        }
        if(getArguments().getString("C") == "GOATS") {
             goat = (Goat) getArguments().getSerializable("C");
            form_dates(goat.getName(),goat.getIdNumber(),goat.getAge(),goat.getState(),goat.getHealth()
                    ,goat.getSex(),goat.getRace(),goat.getImage(),goat.getId());

        }
        if(getArguments().getString("C") == "SHEEPS") {
             sheep = (Sheep) getArguments().getSerializable("C");
            form_dates(sheep.getName(),sheep.getIdNumber(),sheep.getAge(),sheep.getState(),sheep.getHealth()
                    ,sheep.getSex(),sheep.getRace(),sheep.getImage(),sheep.getId());

        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewAux = view;
        add = view.findViewById(R.id.btnAdd);
        cancel = view.findViewById(R.id.btnCancel);
        choose = view.findViewById(R.id.btnChoose);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        choose.setOnClickListener(this);
    }




    private void form_dates(String name, String number, String age, String state, String health,
                            String sex, String race, byte[] image, int id){

        idUp = id;
        nameUp = name;
        numberUp = number;
        ageUp = age;
        stateUp = state;
        healthUp = health;
        SexUp = sex;
        RaceUp = race;
        imageViewAnimal = image;

        edtName = (EditText) viewAux.findViewById(R.id.edtName);
        edtNumber = (EditText) viewAux.findViewById(R.id.edtNumber);
        edtAge = (EditText) viewAux.findViewById(R.id.edtAge);
        edtState = (EditText) viewAux.findViewById(R.id.edtState);
        edtHealth = (EditText) viewAux.findViewById(R.id.edtHealth);
        edtSex = (EditText) viewAux.findViewById(R.id.edtSex);
        edtRace = (EditText) viewAux.findViewById(R.id.edtRace);

        edtName.setText(nameUp);
        edtNumber.setText((numberUp));
        edtAge.setText(ageUp);
        edtState.setText(stateUp);
        edtHealth.setText(healthUp);
        edtSex.setText((SexUp));
        edtRace.setText(RaceUp);


        imageView = viewAux.findViewById(R.id.imageViewForm);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bitmap);


    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnChoose:
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
                break;

            case R.id.btnAdd:

                try {
                    sqLiteHelper.insertData(
                            edtNumber.getText().toString(),
                            edtName.getText().toString(),
                            edtAge.getText().toString(),
                            edtState.getText().toString(),
                            edtHealth.getText().toString(),
                            edtSex.getText().toString(),
                            edtRace.getText().toString(),
                            imageViewToByte(imageView), NameTab
                    );
                    Toast.makeText(getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    //Navigation.findNavController(v).popBackStack();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Added failed", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btnCancel:
                Navigation.findNavController(v).popBackStack();
                break;

            default:
                break;
        }
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
