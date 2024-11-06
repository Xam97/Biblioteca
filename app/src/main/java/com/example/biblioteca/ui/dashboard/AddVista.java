package com.example.biblioteca.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddVista extends ViewModel {
    private final MutableLiveData<String> mText; // Variable para almacenar el texto que se va a mostrar en la UI

    public AddVista() {
        mText = new MutableLiveData<>(); // Inicializa el MutableLiveData

    }

    // Método que permite a la UI observar los cambios en el texto
    public LiveData<String> getText() {
        return mText; // Devuelve un LiveData para que la UI pueda observarlo
    }
}

