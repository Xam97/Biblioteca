package com.example.biblioteca.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.biblioteca.R;
import com.example.biblioteca.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding; // Binding para acceder a los elementos de la UI

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Vincular el layout con ViewBinding para acceder a los elementos de la UI de forma segura
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); // Obtener la vista raíz

        // Configurar el TextView de bienvenida con un mensaje inspirador
        TextView textView = binding.textView2;
        textView.setText("\"Cada libro es un puente hacia una vida más grande, más rica y más profunda.\"");

        // Configurar la imagen de la biblioteca
        ImageView libraryImage = binding.imageView10;
        libraryImage.setImageResource(R.drawable.biblio);  // Asegúrate de tener una imagen llamada 'biblio' en la carpeta 'drawable'

        // Botón de bienvenida (sin funcionalidad en este código, pero listo para añadir eventos si es necesario)
        Button welcomeButton = binding.button;

        return root; // Retornar la vista del fragmento
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Liberar el binding para evitar fugas de memoria
    }
}
