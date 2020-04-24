package com.example.kappgranja.ui.management;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kappgranja.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class PigsFragment extends Fragment {

    RecyclerView recyclerView;
    ListView listView;
    ArrayList<Pig> list;
    AnimalListAdapter adapter = null;
    private String NameTab = "PIGS";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pig, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.list_view);
        list = new ArrayList<>();

        adapter = new AnimalListAdapter(getContext(), R.layout.row_list_animal, list,NameTab);
        listView.setAdapter(adapter);


        // get all data from sqlite
        Cursor cursor = ManagementFragment.sqLiteHelper.getData("SELECT * FROM "+NameTab);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String year = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Pig(name, year, image, id));
        }
        adapter.notifyDataSetChanged();
        ////////////////////////////////////////////////////////////////////////////////////////
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = ManagementFragment.sqLiteHelper.getData("SELECT id FROM "+NameTab);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(getActivity(), arrID.get(position));

                        } else {
                            // delete
                            Cursor c = ManagementFragment.sqLiteHelper.getData("SELECT id FROM "+NameTab);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////

        Button botton_add;
        botton_add = view.findViewById(R.id.add_animal);
        botton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("P", NameTab);
                Navigation.findNavController(v).navigate(R.id.action_pigsFragment_to_animalFormFragment,bundle);

            }
        });

    }


    private void showDialogDelete(final int isA){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());

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
        Cursor cursor = ManagementFragment.sqLiteHelper.getData("SELECT * FROM "+NameTab);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String year = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Pig(name, year, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    ImageView imageViewAnimal;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_animal);
        dialog.setTitle("Update");

        imageViewAnimal = (ImageView) dialog.findViewById(R.id.imageViewAnimal);
        final EditText EditText_Name = (EditText) dialog.findViewById(R.id.EditText_Name);
        final EditText EditText_Year = (EditText) dialog.findViewById(R.id.EditText_Year);
        Button Button_Update = (Button) dialog.findViewById(R.id.Button_Update);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888


                );
            }
        });

        Button_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ManagementFragment.sqLiteHelper.updateData(
                            EditText_Name.getText().toString().trim(),
                            EditText_Year.getText().toString().trim(),
                            ManagementFragment.imageViewToByte(imageViewAnimal),
                            position,NameTab
                    );
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateAnimalList();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        if(requestCode == 888 && resultCode == Activity.RESULT_OK && data != null){
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