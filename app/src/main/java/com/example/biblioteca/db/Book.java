package com.example.biblioteca.db;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;


@Entity(tableName = "books") // Anotación para indicar que esta clase representa una tabla en la base de datos llamada "books"
public class Book implements Parcelable { // Clase Book que implementa Parcelable para permitir la transferencia de sus objetos entre componentes

    @PrimaryKey(autoGenerate = true)
    private int id; // Identificador único del libro, generado automáticamente
    private String title; // Nombre del libro
    private String author; // Autor del libro
    private String synopsis; // Sinopsis o descripción del libro
    private int pageCount; // Número de páginas del libro
    private String genre; // Género literario del libro
    private String publicationDate; // Fecha de publicación del libro
    private String imageUrl; // URL de la imagen de portada del libro

    // Constructor vacío requerido por Room y Parcelable
    public Book() {}

    // Getters y Setters para cada campo de la clase
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getPublicationDate() { return publicationDate; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Constructor para Parcelable, que permite recrear el objeto a partir de un Parcel
    protected Book(Parcel in) {
        id = in.readInt(); // Recupera el valor de 'id' desde el Parcel
        title = in.readString(); // Recupera el valor de 'title' desde el Parcel
        author = in.readString(); // Recupera el valor de 'author' desde el Parcel
        synopsis = in.readString(); // Recupera el valor de 'synopsis' desde el Parcel
        pageCount = in.readInt(); // Recupera el valor de 'pageCount' desde el Parcel
        genre = in.readString(); // Recupera el valor de 'genre' desde el Parcel
        publicationDate = in.readString(); // Recupera el valor de 'publicationDate' desde el Parcel
        imageUrl = in.readString(); // Recupera el valor de 'imageUrl' desde el Parcel
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id); // Escribe el valor de 'id' en el Parcel
        dest.writeString(title); // Escribe el valor de 'title' en el Parcel
        dest.writeString(author); // Escribe el valor de 'author' en el Parcel
        dest.writeString(synopsis); // Escribe el valor de 'synopsis' en el Parcel
        dest.writeInt(pageCount); // Escribe el valor de 'pageCount' en el Parcel
        dest.writeString(genre); // Escribe el valor de 'genre' en el Parcel
        dest.writeString(publicationDate); // Escribe el valor de 'publicationDate' en el Parcel
        dest.writeString(imageUrl); // Escribe el valor de 'imageUrl' en el Parcel
    }

    @Override
    public int describeContents() {
        return 0; // Retorna 0, indicando que no hay contenido especial
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() { // Implementa el Creator necesario para reconstruir objetos Book desde un Parcel
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in); // Crea un nuevo objeto Book a partir de los datos en el Parcel
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size]; // Crea un nuevo arreglo de Book del tamaño especificado
        }
    };
}
