package com.example.mastif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.mastif.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding B;

    public NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(B.getRoot());


        // Setups NavigationController to control the navigation_graph.xml
        NavHostFragment navigationHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navigationHostFragment != null;
        navController = navigationHostFragment.getNavController();
        NavigationUI.setupWithNavController(B.bottomNavigationView, navController);

    }

}