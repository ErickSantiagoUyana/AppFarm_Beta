package com.example.kappgranja.ui.management;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.kappgranja.R;

public class ManagementFragment extends Fragment implements View.OnClickListener {


    private Button button_goats;
    private Button button_sheeps;
    private Button button_cows;
    private Button button_pigs;
    private String sqlMain;

    public static SQLiteHelper sqLiteHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_management, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_goats = view.findViewById(R.id.button_goats);
        button_sheeps = view.findViewById(R.id.button_sheeps);
        button_cows = view.findViewById(R.id.button_cows);
        button_pigs = view.findViewById(R.id.button_pigs);

        button_goats.setOnClickListener(this);
        button_sheeps.setOnClickListener(this);
        button_cows.setOnClickListener(this);
        button_pigs.setOnClickListener(this);
        sqlMain = "(id INTEGER PRIMARY KEY AUTOINCREMENT,idnumber VARCHAR, name VARCHAR,age VARCHAR," +
                "state VARCHAR, health VARCHAR, sex VARCHAR, race VARCHAR, image BLOB)";


        sqLiteHelper = new SQLiteHelper(getContext(), "AnimalsDB.sqlite", null, 4);


        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS COWS"+sqlMain);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS SHEEPS"+sqlMain);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PIGS"+sqlMain);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS GOATS"+sqlMain);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_cows:
                Navigation.findNavController(v).navigate(R.id.action_managementFragment_to_cowsFragment);
                break;
            case R.id.button_goats:
                Navigation.findNavController(v).navigate(R.id.action_managementFragment_to_goatFragment);
                break;
            case R.id.button_pigs:
                Navigation.findNavController(v).navigate(R.id.action_managementFragment_to_pigsFragment);
                break;
            case R.id.button_sheeps:
                Navigation.findNavController(v).navigate(R.id.action_managementFragment_to_sheepsFragment);
                break;
        }
    }


}
