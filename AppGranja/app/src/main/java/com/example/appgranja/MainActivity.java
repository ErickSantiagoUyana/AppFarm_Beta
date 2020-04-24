package com.example.appgranja;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appgranja.ui.management.ManagementFragment;
import com.example.appgranja.ui.notifications.NotificationsFragment;
import com.example.appgranja.ui.security.SecurityFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    /*final Fragment fragment1 = new ManagementFragment();
    final Fragment fragment2 = new SecurityFragment();
    final Fragment fragment3 = new NotificationsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;*/

    private LiveData currentNavController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            if (savedInstanceState == null) {
                this.setupBottomNavigationBar();
            }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        //BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.nav_view);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       // AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
         //    R.id.navigation_management,R.id.navigation_notifications, R.id.navigation_security)
           //   .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(bottomNav, navController);

       // fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit();
        //fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit();
        //fm.beginTransaction().add(R.id.nav_host_fragment, fragment1, "1").commit();

    }

    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.setupBottomNavigationBar();
    }

    private void setupBottomNavigationBar() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        List navGraphIds = CollectionsKt.listOf(new Integer[]{1600001, 1600000, 1600002});
        Intrinsics.checkExpressionValueIsNotNull(bottomNavigationView, "bottomNavigationView");
        FragmentManager var10002 = this.getSupportFragmentManager();
        Intrinsics.checkExpressionValueIsNotNull(var10002, "supportFragmentManager");
        Intent var10004 = this.getIntent();
        Intrinsics.checkExpressionValueIsNotNull(var10004, "intent");
        LiveData controller = NavigationExtensionsKt.setupWithNavController(bottomNavigationView, navGraphIds, var10002, 1000216, var10004);
        controller.observe((LifecycleOwner)this, (Observer)(new Observer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onChanged(Object var1) {
                this.onChanged((NavController)var1);
            }

            public final void onChanged(NavController navController) {
                MainActivity var10000 = MainActivity.this;
                Intrinsics.checkExpressionValueIsNotNull(navController, "navController");
                ActivityKt.setupActionBarWithNavController$default(var10000, navController, (AppBarConfiguration)null, 2, (Object)null);
            }
        }));
        this.currentNavController = controller;
    }

   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_management:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

               case R.id.navigation_security:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.navigation_notifications:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;
            }
            return false;
        }
    };*/

    public boolean onSupportNavigateUp() {
        LiveData var10000 = this.currentNavController;
        boolean var2;
        if (var10000 != null) {
            NavController var1 = (NavController)var10000.getValue();
            if (var1 != null) {
                var2 = var1.navigateUp();
                return var2;
            }
        }
        var2 = false;
        return var2;
    }

}
