package com.example.biblioteca.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.databinding.FragmentPersonBinding;

public class PersonFragment extends Fragment {

    private FragmentPersonBinding binding; // Binding para acceder a los elementos de la UI
    private PerfilVista perfilVista; // ViewModel que gestiona la lógica de datos del perfil

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Obtener el ViewModel de PerfilVista
        perfilVista = new ViewModelProvider(this).get(PerfilVista.class);

        // Inflar el layout del fragmento
        binding = FragmentPersonBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); // Obtenemos la vista raíz

        // Obtener las vistas del layout (TextViews y EditTexts)
        final TextView userNameText = binding.NombreUsuarioValue;
        final TextView userEmailText = binding.UsuarioCorreoValue;
        final TextView userPhoneText = binding.UsuarioTelefonoValue;

        final EditText editUserName = binding.editUserName;
        final EditText editUserEmail = binding.editUserEmail;
        final EditText editUserPhone = binding.editUserPhone;

        final Button btnEdit = binding.btnEdit; // Botón para habilitar la edición
        final Button btnSave = binding.btnSave; // Botón para guardar los cambios

        // Observar los datos del ViewModel y actualizar la UI cuando haya cambios
        perfilVista.getUserName().observe(getViewLifecycleOwner(), userNameText::setText);
        perfilVista.getUserEmail().observe(getViewLifecycleOwner(), userEmailText::setText);
        perfilVista.getUserPhone().observe(getViewLifecycleOwner(), userPhoneText::setText);

        // Configurar el botón de "Editar"
        btnEdit.setOnClickListener(v -> {
            // Mostrar los campos editables y el botón de guardar
            editUserName.setVisibility(View.VISIBLE);
            editUserEmail.setVisibility(View.VISIBLE);
            editUserPhone.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);

            // Rellenar los campos con los valores actuales de los TextViews
            editUserName.setText(userNameText.getText());
            editUserEmail.setText(userEmailText.getText());
            editUserPhone.setText(userPhoneText.getText());

            // Ocultar los TextViews estáticos y el botón de editar
            userNameText.setVisibility(View.GONE);
            userEmailText.setVisibility(View.GONE);
            userPhoneText.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        });

        // Configurar el botón de "Guardar"
        btnSave.setOnClickListener(v -> {
            // Guardar los cambios introducidos por el usuario en el ViewModel
            perfilVista.updateUserName(editUserName.getText().toString());
            perfilVista.updateUserEmail(editUserEmail.getText().toString());
            perfilVista.updateUserPhone(editUserPhone.getText().toString());

            // Actualizar la UI con los nuevos datos
            userNameText.setText(editUserName.getText().toString());
            userEmailText.setText(editUserEmail.getText().toString());
            userPhoneText.setText(editUserPhone.getText().toString());

            // Volver a mostrar los TextViews estáticos y ocultar los campos editables
            editUserName.setVisibility(View.GONE);
            editUserEmail.setVisibility(View.GONE);
            editUserPhone.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);

            // Volver a mostrar el botón de "Editar"
            userNameText.setVisibility(View.VISIBLE);
            userEmailText.setVisibility(View.VISIBLE);
            userPhoneText.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        });

        return root; // Devolver la vista raíz
    }

    // Limpiar el binding cuando la vista del fragmento se destruya
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar fugas de memoria al eliminar el binding
    }
}
