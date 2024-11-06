package com.example.biblioteca.ui.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.biblioteca.databinding.FragmentSearchBinding;
import com.example.biblioteca.db.AppDatabase;
import com.example.biblioteca.db.Book;
import com.example.biblioteca.db.BookDao;
import com.example.biblioteca.ui.BookDetailsDialogFragment;
import com.example.biblioteca.ui.EditBookActivity;
import com.example.biblioteca.ui.adapters.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding; // Binding para acceder a los elementos de la UI
    private BookAdapter bookAdapter; // Adaptador para mostrar los libros en el RecyclerView
    private List<Book> bookList; // Lista de libros a mostrar

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); // Obtenemos la vista raíz

        // Inicializar la lista de libros y el adaptador
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList, new BookAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Book book) {
                // Manejar clic en editar: redirige a la actividad de edición
                Intent intent = new Intent(getContext(), EditBookActivity.class);
                intent.putExtra("book_id", book.getId()); // Envía el ID del libro para cargar sus datos
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Book book) {
                // Manejar clic en eliminar: elimina el libro de la base de datos
                new Thread(() -> {
                    AppDatabase db = AppDatabase.getDatabase(getContext());
                    db.bookDao().delete(book); // Eliminar libro de la base de datos

                    // Actualizar la UI en el hilo principal
                    getActivity().runOnUiThread(() -> {
                        bookList.remove(book); // Eliminar el libro de la lista
                        bookAdapter.notifyDataSetChanged(); // Notificar al adaptador para actualizar la vista
                    });
                }).start();
            }

            @Override
            public void onDetailsClick(Book book) {
                // Manejar clic en detalles: muestra los detalles del libro en un diálogo
                BookDetailsDialogFragment dialogFragment = BookDetailsDialogFragment.newInstance(book);
                dialogFragment.show(getChildFragmentManager(), "BookDetailsDialog");
            }
        });

        // Configurar el RecyclerView para mostrar la lista de libros
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(bookAdapter);

        // Cargar los libros desde la base de datos
        loadBooks();

        // Configurar el SearchView para realizar búsquedas
        SearchView searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Realizar búsqueda cuando el usuario envíe el texto
                searchBooks(query);
                searchView.clearFocus(); // Limpiar el foco después de la búsqueda
                return true; // Indica que el evento ha sido manejado
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrar la lista en tiempo real mientras el usuario escribe
                if (newText.isEmpty()) {
                    loadBooks(); // Cargar todos los libros si no hay texto en la búsqueda
                } else {
                    searchBooks(newText); // Realizar búsqueda basada en el texto ingresado
                }
                return true;
            }
        });

        return root; // Devolver la vista raíz
    }

    // Método para cargar todos los libros desde la base de datos
    private void loadBooks() {
        AppDatabase db = AppDatabase.getDatabase(getContext()); // Obtener la base de datos
        BookDao bookDao = db.bookDao(); // Obtener el DAO para los libros

        // Observar los cambios en los libros y actualizar la UI cuando los datos cambien
        bookDao.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                bookList.clear(); // Limpiar la lista actual
                bookList.addAll(books); // Agregar todos los libros a la lista
                bookAdapter.notifyDataSetChanged(); // Notificar al adaptador para actualizar la vista
            }
        });
    }

    // Método para realizar una búsqueda de libros
    private void searchBooks(String query) {
        AppDatabase db = AppDatabase.getDatabase(getContext()); // Obtener la base de datos
        BookDao bookDao = db.bookDao(); // Obtener el DAO para los libros

        // Observar los cambios en los libros y filtrar los resultados según la búsqueda
        bookDao.getAllBooks().observe(getViewLifecycleOwner(), books -> {
            List<Book> results = new ArrayList<>(); // Lista para almacenar los resultados filtrados

            if (books != null) {
                for (Book book : books) {
                    // Filtrar los libros que contienen el texto de la búsqueda en el título
                    if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        results.add(book);
                    }
                }
            }

            // Actualizar el adaptador con los resultados filtrados
            bookList.clear();
            bookList.addAll(results);
            bookAdapter.notifyDataSetChanged();
        });
    }

    // Limpiar el binding cuando la vista del fragmento se destruya
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar fugas de memoria al eliminar el binding
    }
}
