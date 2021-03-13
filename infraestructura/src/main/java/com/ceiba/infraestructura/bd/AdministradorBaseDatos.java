package com.ceiba.infraestructura.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ceiba.infraestructura.bd.dao.CarroDao;
import com.ceiba.infraestructura.bd.dao.MotoDao;
import com.ceiba.infraestructura.bd.entidad.CarroEntidad;
import com.ceiba.infraestructura.bd.entidad.MotoEntidad;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CarroEntidad.class, MotoEntidad.class}, version = 1)
public abstract class AdministradorBaseDatos extends RoomDatabase {

    private static AdministradorBaseDatos instancia = null;

    private static final int NUMERO_DE_HILOS = 4;
    public static final ExecutorService EJECUTOR_ESCRITURA_BD = Executors.newFixedThreadPool(NUMERO_DE_HILOS);

    public abstract CarroDao carDao();

    public abstract MotoDao motoDao();

    public static AdministradorBaseDatos obtenerInstancia(Context contexto) {
        if (instancia == null) {
            synchronized (AdministradorBaseDatos.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(contexto,
                            AdministradorBaseDatos.class, "adn-ceiba").build();
                }
            }
        }
        return instancia;
    }
}
