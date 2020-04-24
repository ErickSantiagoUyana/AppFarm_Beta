package com.example.appgranja.ui.management;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.appgranja.CowsFragment;
import com.example.appgranja.GoatsFragment;
import com.example.appgranja.PigsFragment;
import com.example.appgranja.R;
import com.example.appgranja.SheepsFragment;

public class ManagementFragment extends Fragment implements View.OnClickListener {

    private ManagementViewModel homeViewModel;
   // private Context boton1;
    // private Button boton2;
    private Button button_goats;
    private Button button_sheeps;
    private Button button_cows;
    private Button button_pigs;

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

   }


    @Override
    public void onClick(View v) {
    switch (v.getId())
    {
        case R.id.button_cows:

           // Navigation.findNavController(v).navigate(R.id.action_navigation_management_to_navigation_cows);
            CowsFragment cowsFragment = new CowsFragment();
            FragmentManager fragmentManagerC = getParentFragmentManager();
            fragmentManagerC.beginTransaction().replace(R.id.nav_host_fragment,cowsFragment).addToBackStack(null).commit();
            break;
        case R.id.button_goats:
            //Navigation.findNavController(v).navigate(R.id.action_navigation_management_to_navigation_goats);

            GoatsFragment goatsFragment = new GoatsFragment();
            FragmentManager fragmentManagerG = getParentFragmentManager();
            fragmentManagerG.beginTransaction().replace(R.id.nav_host_fragment,goatsFragment).addToBackStack(null).commit();
            break;
        case R.id.button_pigs:
            //Navigation.findNavController(v).navigate(R.id.action_navigation_management_to_navigation_pigs);

            PigsFragment pigsFragment = new PigsFragment();
            FragmentManager fragmentManagerP = getParentFragmentManager();
            fragmentManagerP.beginTransaction().replace(R.id.nav_host_fragment,pigsFragment).addToBackStack(null).commit();

            break;
        case R.id.button_sheeps:
            //Navigation.findNavController(v).navigate(R.id.action_navigation_management_to_navigation_sheeps);

            SheepsFragment sheepsFragment = new SheepsFragment();
            FragmentManager fragmentManagerS = getParentFragmentManager();
            fragmentManagerS.beginTransaction().replace(R.id.nav_host_fragment,sheepsFragment).addToBackStack(null).commit();

            break;
    }
    }
}
