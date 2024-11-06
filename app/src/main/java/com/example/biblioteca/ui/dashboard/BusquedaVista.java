package com.example.biblioteca.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusquedaVista extends ViewModel {
    private final MutableLiveData<String> mText;

    // Constructor
    public BusquedaVista() {
        mText = new MutableLiveData<>();
    }

    // Método para obtener el valor de mText
    public LiveData<String> getText() {
        return mText;
    }
}

