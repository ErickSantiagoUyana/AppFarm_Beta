package com.example.kappgranja.ui.management;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.Navigation;

import com.example.kappgranja.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AnimalUpdateFragment extends Fragment implements View.OnClickListener{


    private EditText edtName, edtNumber,edtAge,edtState, edtHealth, edtSex,edtRace;
    private Button btnChoose, btnAdd, btnCancel;
    private TextView textname;
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


    private Cow cow;
    private Pig pig;
    private Goat goat;
    private Sheep sheep;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal_form, container, false);

        viewAux = view;
        NameTab = getArguments().getString("C");



        if(NameTab == "COWS") {

           cow = (Cow) getArguments().getSerializable("P");
           form_dates(cow.getName(),cow.getIdNumber(),cow.getAge(),cow.getState(),cow.getHealth()
           ,cow.getSex(),cow.getRace(),cow.getImage(),cow.getId());


        }
        if(NameTab == "PIGS") {
             pig = (Pig) getArguments().getSerializable("P");
            form_dates(pig.getName(),pig.getIdNumber(),pig.getAge(),pig.getState(),pig.getHealth()
                    ,pig.getSex(),pig.getRace(),pig.getImage(),pig.getId());

        }
        if(NameTab == "GOATS") {
             goat = (Goat) getArguments().getSerializable("P");
            form_dates(goat.getName(),goat.getIdNumber(),goat.getAge(),goat.getState(),goat.getHealth()
                  ,goat.getSex(),goat.getRace(),goat.getImage(),goat.getId());

        }
        if(NameTab == "SHEEPS") {
             sheep = (Sheep) getArguments().getSerializable("P");
            form_dates(sheep.getName(),sheep.getIdNumber(),sheep.getAge(),sheep.getState(),sheep.getHealth()
                    ,sheep.getSex(),sheep.getRace(),sheep.getImage(),sheep.getId());

        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void form_dates(String name, String number, String age, String state, String health,
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

        edtName =  viewAux.findViewById(R.id.edtName);
        edtNumber =  viewAux.findViewById(R.id.edtNumber);
        edtAge =  viewAux.findViewById(R.id.edtAge);
        edtState =  viewAux.findViewById(R.id.edtState);
        edtHealth =  viewAux.findViewById(R.id.edtHealth);
        edtSex = viewAux.findViewById(R.id.edtSex);
        edtRace = viewAux.findViewById(R.id.edtRace);


        textname = viewAux.findViewById(R.id.new_animal);
        textname.setText("Update "+nameUp);


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



        btnChoose = viewAux.findViewById(R.id.btnChoose);
        btnCancel = viewAux.findViewById(R.id.btnCancel);
        btnAdd =  viewAux.findViewById(R.id.btnAdd);
        btnChoose.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        sqLiteHelper = ManagementFragment.sqLiteHelper;

        switch (v.getId()) {

            case R.id.btnChoose:
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
                break;

            case R.id.btnAdd:

                String numCadena= String.valueOf(idUp);
                try {
                    sqLiteHelper.updateData(
                            edtNumber.getText().toString(),
                            edtName.getText().toString(),
                            edtAge.getText().toString(),
                            edtState.getText().toString(),
                            edtHealth.getText().toString(),
                            edtSex.getText().toString(),
                            edtRace.getText().toString(),
                            imageViewToByte(imageView),
                            numCadena,NameTab
                    );
                    Toast.makeText(getContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Toast.makeText(getContext(), "ERROR!",Toast.LENGTH_SHORT).show();
                    Log.e("Update error", error.getMessage());
                }

                break;
            case R.id.btnCancel:
                Navigation.findNavController(v).popBackStack();
                break;

            default:
                break;
        }
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

}
