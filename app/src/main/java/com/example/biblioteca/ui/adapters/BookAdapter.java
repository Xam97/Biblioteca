package com.example.biblioteca.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteca.R;
import com.example.biblioteca.db.Book;
import com.squareup.picasso.Picasso;

import java.util.List;
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList; // Lista de libros que el adaptador mostrará
    private OnItemClickListener onItemClickListener; // Interfaz para manejar clics en los botones de cada elemento

    // Interfaz que define los métodos para manejar los clics en botones de edición, eliminación y detalles
    public interface OnItemClickListener {
        void onEditClick(Book book);
        void onDeleteClick(Book book);
        void onDetailsClick(Book book);
    }

    // Constructor del adaptador que recibe la lista de libros y el listener para los clics
    public BookAdapter(List<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList;
        this.onItemClickListener = listener;
    }

    // Crea y devuelve un nuevo ViewHolder cuando RecyclerView necesita uno
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new BookViewHolder(view);
    }

    // Asigna datos al ViewHolder en la posición especificada
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position); // Obtiene el libro en la posición actual

        holder.textNombreLibro.setText(book.getTitle()); // Asigna el título del libro al TextView
        holder.textGenero.setText(book.getGenre()); // Asigna el género del libro al TextView
        holder.textPaginas.setText(String.valueOf(book.getPageCount())); // Asigna el número de páginas al TextView

        // Carga la imagen de portada del libro usando Picasso, o muestra una imagen por defecto si no hay URL
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            Picasso.get().load(book.getImageUrl()).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.book); // Imagen por defecto si no hay URL
        }

        // Define el comportamiento de los botones para cada acción
        holder.buttonEdit.setOnClickListener(v -> onItemClickListener.onEditClick(book));
        holder.buttonDelete.setOnClickListener(v -> onItemClickListener.onDeleteClick(book));
        holder.buttonDetails.setOnClickListener(v -> onItemClickListener.onDetailsClick(book));
    }

    // Devuelve el número de elementos en la lista de libros
    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // Método para actualizar la lista de libros y notificar a RecyclerView del cambio
    public void updateList(List<Book> newBookList) {
        bookList.clear();
        bookList.addAll(newBookList);
        notifyDataSetChanged(); // Notifica que los datos han cambiado y se debe actualizar la vista
    }

    // Clase ViewHolder para almacenar las vistas de cada elemento de la lista
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // Imagen de la portada del libro
        TextView textNombreLibro, textGenero, textPaginas; // TextViews para mostrar el título, género y páginas
        Button buttonEdit, buttonDelete, buttonDetails; // Botones de edición, eliminación y detalles

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagelibro); // Referencia a la ImageView
            textNombreLibro = itemView.findViewById(R.id.text_nombre_libro); // Referencia al TextView del título
            textGenero = itemView.findViewById(R.id.text_genero); // Referencia al TextView del género
            textPaginas = itemView.findViewById(R.id.text_paginas); // Referencia al TextView del número de páginas
            buttonEdit = itemView.findViewById(R.id.button_edit); // Referencia al botón de edición
            buttonDelete = itemView.findViewById(R.id.button_eliminar); // Referencia al botón de eliminación
            buttonDetails = itemView.findViewById(R.id.button_detalles); // Referencia al botón de detalles
        }
    }
}
