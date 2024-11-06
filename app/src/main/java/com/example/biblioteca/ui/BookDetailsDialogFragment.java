package com.example.biblioteca.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.biblioteca.R;
import com.example.biblioteca.db.Book;

public class BookDetailsDialogFragment extends DialogFragment {
    private static final String ARG_BOOK = "book"; // Clave constante para pasar el objeto Book como argumento

    private Book book; // Objeto Book que se usará para mostrar detalles del libro

    // Método estático para crear una nueva instancia del diálogo con el objeto Book como argumento
    public static BookDetailsDialogFragment newInstance(Book book) {
        BookDetailsDialogFragment fragment = new BookDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, book); // Usa putParcelable para pasar el objeto Book
        fragment.setArguments(args); // Asigna los argumentos al fragmento
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Recupera el objeto Book de los argumentos si no es nulo
            book = getArguments().getParcelable(ARG_BOOK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla el layout del diálogo que mostrará los detalles del libro
        View view = inflater.inflate(R.layout.dialog_book_details, container, false);

        // Obtiene las referencias a los TextViews del layout
        TextView titleTextView = view.findViewById(R.id.text_title);
        TextView genreTextView = view.findViewById(R.id.text_genre);
        TextView pageCountTextView = view.findViewById(R.id.text_page_count);
        TextView authorTextView = view.findViewById(R.id.text_author);
        TextView publishDateTextView = view.findViewById(R.id.text_publish_date);

        if (book != null) {
            // Si el objeto Book no es nulo, establece los datos del libro en cada TextView
            titleTextView.setText(book.getTitle()); // Muestra el título
            genreTextView.setText(book.getGenre()); // Muestra el género
            pageCountTextView.setText(String.valueOf(book.getPageCount())); // Muestra el número de páginas
            authorTextView.setText(book.getAuthor()); // Muestra el autor
            publishDateTextView.setText(book.getPublicationDate()); // Muestra la fecha de publicación
        }

        return view; // Retorna la vista configurada del diálogo
    }
}
