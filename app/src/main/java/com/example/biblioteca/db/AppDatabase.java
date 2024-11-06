package com.example.biblioteca.db; // Define el paquete en el que se encuentra esta clase

import androidx.room.Database; // Importa la anotación Database de Room
import androidx.room.Room; // Importa la clase Room para crear la base de datos
import androidx.room.RoomDatabase; // Importa la clase base RoomDatabase de la que heredará AppDatabase
import android.content.Context; // Importa la clase Context para usar el contexto de la aplicación

@Database(entities = {Book.class}, version = 1) // Anotación que define esta clase como base de datos de Room; incluye la entidad 'Book' y especifica la versión de la base de datos
public abstract class AppDatabase extends RoomDatabase { // Declara la clase abstracta AppDatabase que extiende RoomDatabase
    public abstract BookDao bookDao(); // Método abstracto que retorna el DAO (Data Access Object) para realizar operaciones de base de datos con la entidad Book

    private static volatile AppDatabase INSTANCE; // Declara una instancia única (singleton) de AppDatabase, marcada como 'volatile' para garantizar visibilidad en hilos

    // Método para obtener la instancia de la base de datos
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { // Verifica si la instancia ya existe
            synchronized (AppDatabase.class) { // Bloquea la clase para evitar que múltiples hilos creen diferentes instancias
                if (INSTANCE == null) { // Doble comprobación para crear la instancia si aún es null
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database") // Crea la base de datos usando Room; le asigna un nombre "app_database"
                            .build(); // Construye la instancia de AppDatabase
                }
            }
        }
        return INSTANCE; // Devuelve la instancia de la base de datos
    }
}
