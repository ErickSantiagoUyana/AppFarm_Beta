package com.example.appgranja;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appgranja.ui.notifications.NotificationsFragment;
import com.example.appgranja.ui.notifications.NotificationsViewModel;

public class GoatsFragment extends Fragment  {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_goat, container, false);
        return  vista;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* OnBackPressedCallback callback = new OnBackPressedCallback(true  enabled by default/) {
            @Override
            public void handleOnBackPressed() {
                Context context = getContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);*/

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

}
