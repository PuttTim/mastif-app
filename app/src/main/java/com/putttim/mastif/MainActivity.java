package com.putttim.mastif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    // Following Android's naming convention of Resources = R,
    // all of the app's view bindings will be named B.
    private ActivityMainBinding B;

    private SharedViewModel sharedVM;

    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = ActivityMainBinding.inflate(getLayoutInflater());

        // Sets view as activity_main.xml
        setContentView(B.getRoot());
        sharedVM = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedVM.startupValueSet();


//        sharedVM.getPlaylistList();

        // Setups NavigationController to control the navigation_graph.xml
        NavHostFragment navigationHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navigationHostFragment != null;
        navController = navigationHostFragment.getNavController();
        NavigationUI.setupWithNavController(B.bottomNavigationView, navController);
    }
}