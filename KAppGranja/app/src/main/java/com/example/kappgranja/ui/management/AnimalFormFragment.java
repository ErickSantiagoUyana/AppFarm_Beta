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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.kappgranja.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AnimalFormFragment extends Fragment implements View.OnClickListener {

    private EditText edtName, edtYear;
    private Button btnChoose, btnAdd, btnCancel;
    private ImageView imageView;
    private int REQUEST_CODE_GALLERY = 999;
    public static SQLiteHelper sqLiteHelper;
    private String NameTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal_form, container, false);
        NameTab = getArguments().getString("P");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        sqLiteHelper = ManagementFragment.sqLiteHelper;

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
                            edtName.getText().toString().trim(),
                            edtYear.getText().toString().trim(),
                            imageViewToByte(imageView), NameTab
                    );
                    Toast.makeText(getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtYear.setText("");
                    imageView.setImageResource(R.drawable.img_add_animals);
                } catch (Exception e) {
                    e.printStackTrace();
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

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
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

    private void init(View view) {
        //BUTTONS
        btnChoose = (Button) view.findViewById(R.id.btnChoose);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnChoose.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        //EDITTEXT
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtYear = (EditText) view.findViewById(R.id.edtYear);
        //IMAGEVIEW
        imageView = (ImageView) view.findViewById(R.id.imageView);
    }
}
