package com.example.kappgranja.ui.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.kappgranja.R;

import java.util.ArrayList;


public class CowsFragment extends Fragment {

    private ListView listView;
    private ArrayList<Cow> list;
    private AnimalListAdapter adapter;
    private String NameTab = "COWS";
    private ImageButton botton_add;
     public SQLiteHelper sqLiteHelper;
    private View vaux;

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

        ImageView imagen =  dialog.findViewById(R.id.imageViewDetail);

        TextView texName =  dialog.findViewById(R.id.textNameDetail);
        TextView textNumber = dialog.findViewById(R.id.textNumberDetail);
        TextView textAge =  dialog.findViewById(R.id.textAgeDetail);
        TextView textState =  dialog.findViewById(R.id.textStateDetail);
        TextView textHealth = dialog.findViewById(R.id.textHealthDetail);
        TextView textSex = dialog.findViewById(R.id.textSexDetail);
        TextView textRace = dialog.findViewById(R.id.textRaceDetail);

        Button btnUpdateDetail = dialog.findViewById(R.id.btnUpdateDetail);
        Button btnDeleteDetail =  dialog.findViewById((R.id.btnDeleteDetail));

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

            @Override
            public void onClick(View v) {

                showDialogDelete(list.get(position).getId());
                dialog.dismiss();
            }

        });
        btnUpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("C",NameTab);
                bundle.putSerializable("P",list.get(position));
                Navigation.findNavController(vaux).navigate(R.id.action_cowsFragment_to_animalUpdateFragment,bundle);
                dialog.cancel();


            }
        });


    }

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

}