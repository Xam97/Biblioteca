package com.example.biblioteca;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.biblioteca.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; // Objeto para acceder a las vistas a través de ViewBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa ViewBinding para el layout de la actividad principal
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura el BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configura la navegación para que excluya el fragmento de "notificaciones"
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_person, R.id.navigation_search, R.id.navigation_add)
                .build();

        // Configura el controlador de navegación para el fragmento principal
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Establece la `ActionBar` para que funcione con el controlador de navegación y el AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Configura el BottomNavigationView para que se sincronice con el NavController
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Oculta el título en la ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
