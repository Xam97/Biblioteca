package com.example.biblioteca.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.databinding.FragmentAddBinding;
import com.example.biblioteca.db.AppDatabase;
import com.example.biblioteca.db.Book;
import com.example.biblioteca.db.BookDao;

import java.util.List;
public class AddFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 1; // Constante para el código de solicitud de galería

    private FragmentAddBinding binding; // Enlace con la vista del fragmento
    private AppDatabase db; // Instancia de la base de datos
    private BookDao bookDao; // Acceso a la interfaz de la base de datos
    private Uri imageUri; // URI para almacenar la imagen seleccionada

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el ViewModel de este fragmento
        AddVista addViewModel = new ViewModelProvider(this).get(AddVista.class);

        binding = FragmentAddBinding.inflate(inflater, container, false); // Vincula la vista con el fragmento
        View root = binding.getRoot(); // Obtiene la raíz de la vista

        // Inicializar la base de datos
        db = AppDatabase.getDatabase(requireContext());
        bookDao = db.bookDao(); // Accede al DAO para interactuar con la base de datos

        // Agregar un Observer para observar cambios en la lista de libros
        bookDao.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                // Muestra el número de libros en un Toast cuando cambia la lista
                if (books != null) {
                    Toast.makeText(getActivity(), "Número de libros: " + books.size(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Captura las vistas de los campos de entrada
        final EditText tituloLibroInput = binding.TituloLibro;
        final EditText autorLibroInput = binding.AutorLibro;
        final EditText sinopsisLibroInput = binding.SinopsisLibro;
        final EditText paginasLibroInput = binding.PaginasLibro;
        final EditText generoLibroInput = binding.GeneroLibro;
        final EditText fechaPublicacionInput = binding.FechaPublicacionLibro;
        final ImageView portadasImageView = binding.PortadaLibro;
        final Button botonSeleccionarImagen = binding.buttonPortada;
        final Button botonAgregar = binding.botonAgregar;

        // Configura el clic para abrir la galería de imágenes
        botonSeleccionarImagen.setOnClickListener(v -> openGallery());

        // Configura el clic para guardar el libro
        botonAgregar.setOnClickListener(v -> saveBook(
                tituloLibroInput,
                autorLibroInput,
                sinopsisLibroInput,
                paginasLibroInput,
                generoLibroInput,
                fechaPublicacionInput,
                portadasImageView
        ));

        return root; // Devuelve la vista inflada
    }

    // Método para abrir la galería y seleccionar una imagen
    private void openGallery() {
        // Crea una intención para abrir la galería de imágenes
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE); // Lanza la actividad para seleccionar una imagen
    }

    // Método que recibe el resultado de la galería (imagen seleccionada)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            imageUri = data.getData(); // Almacena la URI de la imagen seleccionada
            binding.PortadaLibro.setImageURI(imageUri); // Muestra la imagen en el ImageView
        }
    }

    // Método para guardar un libro en la base de datos
    private void saveBook(EditText tituloLibroInput, EditText autorLibroInput, EditText sinopsisLibroInput,
                          EditText paginasLibroInput, EditText generoLibroInput, EditText fechaPublicacionInput,
                          ImageView portadasImageView) {
        // Obtiene los valores de los campos de entrada
        String titulo = tituloLibroInput.getText().toString();
        String autor = autorLibroInput.getText().toString();
        String sinopsis = sinopsisLibroInput.getText().toString();
        String paginasString = paginasLibroInput.getText().toString();
        String genero = generoLibroInput.getText().toString();
        String fechaPublicacion = fechaPublicacionInput.getText().toString();

        // Verifica que los campos no estén vacíos
        if (titulo.isEmpty() || autor.isEmpty() || sinopsis.isEmpty() || paginasString.isEmpty() || genero.isEmpty() || fechaPublicacion.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return; // Si algún campo está vacío, muestra un mensaje y termina el proceso
        }

        // Convierte el número de páginas a entero y maneja excepciones si es necesario
        int paginas;
        try {
            paginas = Integer.parseInt(paginasString);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Por favor, ingrese un número válido para las páginas.", Toast.LENGTH_SHORT).show();
            return; // Si hay un error al convertir, muestra un mensaje y termina
        }

        // Crea un nuevo objeto Book
        Book newBook = new Book();
        newBook.setTitle(titulo);
        newBook.setAuthor(autor);
        newBook.setSynopsis(sinopsis);
        newBook.setPageCount(paginas);
        newBook.setGenre(genero);
        newBook.setPublicationDate(fechaPublicacion);
        // Añade la URL de la imagen si se seleccionó una imagen
        if (imageUri != null) {
            newBook.setImageUrl(imageUri.toString());
        }

        // Guarda el libro en la base de datos en un hilo separado
        new Thread(() -> {
            try {
                bookDao.insert(newBook); // Inserta el libro en la base de datos
            } catch (Exception e) {
                e.printStackTrace(); // Si ocurre un error, lo imprime en Logcat
            }
            // Muestra un mensaje de éxito en el hilo principal
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "Libro guardado", Toast.LENGTH_SHORT).show();
                // Limpia los campos de entrada después de guardar el libro
                clearFields(tituloLibroInput, autorLibroInput, sinopsisLibroInput, paginasLibroInput, generoLibroInput, fechaPublicacionInput, portadasImageView);
            });
        }).start();
    }

    // Método para limpiar los campos después de guardar un libro
    private void clearFields(EditText titulo, EditText autor, EditText sinopsis, EditText paginas,
                             EditText genero, EditText fechaPublicacion, ImageView portada) {
        titulo.setText(""); // Limpia el campo de texto del título
        autor.setText(""); // Limpia el campo del autor
        sinopsis.setText(""); // Limpia el campo de la sinopsis
        paginas.setText(""); // Limpia el campo de las páginas
        genero.setText(""); // Limpia el campo del género
        fechaPublicacion.setText(""); // Limpia el campo de la fecha
        portada.setImageResource(0); // Limpia la imagen de la portada
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evita posibles fugas de memoria liberando la referencia al binding
    }
}

