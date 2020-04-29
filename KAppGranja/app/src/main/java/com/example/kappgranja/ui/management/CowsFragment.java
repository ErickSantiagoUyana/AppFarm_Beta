package com.example.kappgranja.ui.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.kappgranja.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class CowsFragment extends Fragment {

    private ListView listView;
    private ArrayList<Cow> list;
    private AnimalListAdapter adapter;
    private String NameTab = "COWS";
    private ImageButton botton_add;
    private int REQUEST_CODE_GALLERY = 888;
    private SQLiteHelper sqLiteHelper;
    private View vaux;
    private  ImageView imageViewAnimal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new AnimalListAdapter(getContext(), R.layout.row_list_animal, list,NameTab);
        listView.setAdapter(adapter);
        botton_add = view.findViewById(R.id.add_animal2);
        sqLiteHelper = ManagementFragment.sqLiteHelper;
        vaux = view;
        // get all data from sqlite
        updateAnimalList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showDialogDetail(position,getActivity());

            }
        });

        botton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("P", NameTab);
                Navigation.findNavController(v).navigate(R.id.action_cowsFragment_to_animalFormFragment,bundle);

            }
        });

    }


    private void showDialogDetail(int position, Activity activity){


        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.fragment_animal_detail);

        ImageView imagen = (ImageView) dialog.findViewById(R.id.imageViewDetail);

        TextView texName = (TextView) dialog.findViewById(R.id.textNameDetail);
        TextView textNumber = (TextView) dialog.findViewById(R.id.textNumberDetail);
        TextView textAge = (TextView) dialog.findViewById(R.id.textAgeDetail);
        TextView textState = (TextView) dialog.findViewById(R.id.textStateDetail);
        TextView textHealth = (TextView) dialog.findViewById(R.id.textHealthDetail);
        TextView textSex = (TextView) dialog.findViewById(R.id.textSexDetail);
        TextView textRace = (TextView) dialog.findViewById(R.id.textRaceDetail);

        Button btnUpdateDetail = (Button) dialog.findViewById(R.id.btnUpdateDetail);
        Button btnDeleteDetail = (Button) dialog.findViewById((R.id.btnDeleteDetail));

        texName.setText(list.get(position).getName());
        textNumber.setText(list.get(position).getIdNumber());
        textAge.setText(list.get(position).getAge());
        textState.setText(list.get(position).getState());
        textHealth.setText(list.get(position).getHealth());
        textSex.setText(list.get(position).getSex());
        textRace.setText(list.get(position).getRace());

        byte[] foodImage = list.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        imagen.setImageBitmap(bitmap);

        // set width for dialog
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width,height);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
        btnDeleteDetail.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());

            @Override
            public void onClick(View v) {

                showDialogDelete(list.get(position).getId());
                dialog.dismiss();
            }

        });
        btnUpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showDialogUpdate(activity,list.get(position).getId());
            //dialog.dismiss();
                //int out = Integer.parseInt(list.get(position).toString());
                Bundle bundle = new Bundle();
                bundle.putString("C",NameTab);
                bundle.putSerializable("P",list.get(position));
                Navigation.findNavController(vaux).navigate(R.id.action_cowsFragment_to_animalUpdateFragment,bundle);
                //dialog.dismiss();
                dialog.cancel();


            }
        });


    }

    /*private void showDialogUpdate(int isA, Activity activity){

        Dialog dialogUpdate = new Dialog(activity);
        dialogUpdate.setContentView(R.layout.update_animal);
        // set width for dialog
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogUpdate.getWindow().setLayout(width,height);
        dialogUpdate.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialogUpdate.show();

    }*/
    private void showDialogDelete(int isA){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ManagementFragment.sqLiteHelper.deleteData(isA,NameTab);
                    Toast.makeText(getContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateAnimalList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }



    private void updateAnimalList(){
        // get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM "+NameTab);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String idNumber = cursor.getString(1);
            String name = cursor.getString(2);
            String age = cursor.getString(3);
            String state = cursor.getString(4);
            String health = cursor.getString(5);
            String sex = cursor.getString(6);
            String race = cursor.getString(7);
            byte[] image = cursor.getBlob(8);

            list.add(new Cow(idNumber,name,age,state,health,sex,race,image,id));
        }
        adapter.notifyDataSetChanged();
    }



   /*

    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_animal);
        dialog.setTitle("Update");

        imageViewAnimal = (ImageView) dialog.findViewById(R.id.imageViewUpdate);
        final EditText EditText_Name = (EditText) dialog.findViewById(R.id.edtNameUpdate);
        final EditText EditText_Year = (EditText) dialog.findViewById(R.id.edtYearUpdate);
        Button Button_Update = (Button) dialog.findViewById(R.id.btnAddUpdate);
        Button Button_Cancel = (Button) dialog.findViewById((R.id.btnCancelUpdate));

        // set width for dialog
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // set height for dialog

        dialog.getWindow().setLayout(width,height);
        //dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();


    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
*/


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
                imageViewAnimal.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}