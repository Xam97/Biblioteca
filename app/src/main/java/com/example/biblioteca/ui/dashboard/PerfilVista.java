package com.example.biblioteca.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class PerfilVista extends ViewModel {
    private final MutableLiveData<String> userName;
    private final MutableLiveData<String> userEmail;
    private final MutableLiveData<String> userPhone;

    // Constructor
    public PerfilVista() {
        userName = new MutableLiveData<>();
        userEmail = new MutableLiveData<>();
        userPhone = new MutableLiveData<>();

        // Inicializamos con valores por defecto o con valores que provienen de la base de datos
        userName.setValue("Sofia Morel");
        userEmail.setValue("sofia@ejemplo.com");
        userPhone.setValue("123456789");
    }

    // Métodos para obtener los datos
    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public LiveData<String> getUserPhone() {
        return userPhone;
    }

    // Métodos para actualizar los datos
    public void updateUserName(String newName) {
        userName.setValue(newName);
    }

    public void updateUserEmail(String newEmail) {
        userEmail.setValue(newEmail);
    }

    public void updateUserPhone(String newPhone) {
        userPhone.setValue(newPhone);
    }
}

