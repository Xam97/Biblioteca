package com.example.biblioteca.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;

import java.util.List;
@Dao // Anotación para indicar que esta interfaz es un Data Access Object (DAO) para Room
public interface BookDao {

    // Método para insertar un libro en la base de datos
    @Insert
    void insert(Book book);

    // Método para obtener todos los libros de la base de datos
    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks(); // Devuelve una lista de libros envuelta en LiveData para observar los cambios en tiempo real

    // Método para obtener un libro por su título
    @Query("SELECT * FROM books WHERE title = :bookTitle LIMIT 1")
    Book getBookByTitle(String bookTitle); // Busca el primer libro que coincida con el título especificado

    // Método para actualizar los datos de un libro
    @Update
    void update(Book book);

    // Método para eliminar un libro de la base de datos
    @Delete
    void delete(Book book);

    // Método para obtener un libro por su ID
    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    Book getBookById(int id); // Busca el primer libro que coincida con el ID especificado
}
