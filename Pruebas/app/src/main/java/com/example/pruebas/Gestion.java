package com.example.pruebas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class Gestion extends Fragment {


    private GestionViewModel gestionViewModel;
    private Button boton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gestionViewModel =
                ViewModelProviders.of(this).get(GestionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gestion, container, false);
        final TextView textView = root.findViewById(R.id.text_seguridad);
        gestionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_navigation_gestion_to_blankFragment);
        Button button = view.findViewById(R.id.boton_oveja);
        button.setOnClickListener(s);

    }


}

