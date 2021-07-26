package com.example.mastif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.mastif.Fragments.PlayerFragment;
import com.example.mastif.Objects.Song;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.ViewModels.QueueViewModel;
import com.example.mastif.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private QueueViewModel queueVM;
    private PlayerViewModel playerVM;
    private ActivityMainBinding B;

    public NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = ActivityMainBinding.inflate(getLayoutInflater());
        queueVM = new ViewModelProvider(this).get(QueueViewModel.class);
        playerVM = new ViewModelProvider(this).get(PlayerViewModel.class);

        MediaPlayer player = new MediaPlayer();

        playerVM.setMediaPlayer(player);

        // Sets view as activity_main.xml
        setContentView(B.getRoot());

        // Setups NavigationController to control the navigation_graph.xml
        NavHostFragment navigationHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navigationHostFragment != null;
        navController = navigationHostFragment.getNavController();
        NavigationUI.setupWithNavController(B.bottomNavigationView, navController);

    }




}