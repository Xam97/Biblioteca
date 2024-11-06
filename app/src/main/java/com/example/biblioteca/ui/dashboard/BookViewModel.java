package com.example.biblioteca.ui.dashboard;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.biblioteca.db.AppDatabase;
import com.example.biblioteca.db.Book;
import com.example.biblioteca.db.BookDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookViewModel extends AndroidViewModel {
    private final BookDao bookDao; // Interfaz DAO para interactuar con la base de datos
    private final LiveData<List<Book>> allBooks; // Objeto LiveData que contiene la lista de todos los libros
    private final ExecutorService executorService; // Executor para manejar operaciones en segundo plano

    // Constructor
    public BookViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application); // Obtener la base de datos
        bookDao = db.bookDao(); // Obtener el DAO de los libros
        allBooks = bookDao.getAllBooks(); // Obtener la lista de todos los libros
        executorService = Executors.newSingleThreadExecutor(); // Executor para operaciones en un solo hilo
    }

    // Método para obtener todos los libros
    public LiveData<List<Book>> getAllBooks() {
        return allBooks; // Devuelve LiveData de la lista de libros para ser observado por la UI
    }

    // Método para insertar un libro
    public void insert(Book book) {
        executorService.execute(() -> bookDao.insert(book)); // Ejecutar en un hilo en segundo plano
    }

    // Método para actualizar un libro
    public void update(Book book) {
        executorService.execute(() -> bookDao.update(book)); // Ejecutar en un hilo en segundo plano
    }

    // Método para eliminar un libro
    public void delete(Book book) {
        executorService.execute(() -> bookDao.delete(book)); // Ejecutar en un hilo en segundo plano
    }
}

