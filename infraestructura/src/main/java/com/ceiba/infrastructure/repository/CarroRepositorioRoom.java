package com.ceiba.infrastructure.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.infrastructure.anticorruption.CarTranslator;
import com.ceiba.infrastructure.db.DataBaseManager;
import com.ceiba.infrastructure.db.dao.CarDao;
import com.ceiba.infrastructure.db.entity.CarEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class CarroRepositorioRoom implements CarroRepositorio {

    private DataBaseManager dataBaseManager;
    private CarDao carDao;

    @Inject
    public CarroRepositorioRoom(@ApplicationContext Context context) {
        dataBaseManager = DataBaseManager.getInstance(context);
        carDao = dataBaseManager.carDao();
    }

    @Override
    public List<Carro> obtenerCarros() {
        List<Carro> carsDomain = new ArrayList<>();
        CarsGetAsyncTask carsGetAsyncTask = new CarsGetAsyncTask();
        try {
            List<CarEntity> carsDB = carsGetAsyncTask.execute().get();
            CarTranslator.listCarsFromEntityToEntityDB(carsDB);
        } catch (Exception e) {
            Log.e("AsyncTask", e.getMessage());
        }
        return carsDomain;
    }

    @Override
    public void guardarCarro(Carro carro) {
        CarInsertAsyncTask carInsertAsyncTask = new CarInsertAsyncTask();
        CarEntity carEntity = CarTranslator.fromCarDomainToCarBD(carro);
        carInsertAsyncTask.execute(carEntity);
    }

    @Override
    public void eliminarCarro(Carro carro) {

    }

    @Override
    public byte obtenerCantidadCarros() {
        return 0;
    }

    class CarsGetAsyncTask extends AsyncTask<Void, Void, List<CarEntity>> {
        @Override
        protected List<CarEntity> doInBackground(Void... voids) {
            return carDao.getAllCars();
        }
    }

    class CarInsertAsyncTask extends AsyncTask<CarEntity, Void, Void> {
        @Override
        protected Void doInBackground(CarEntity... carEntities) {
            carDao.insertCar(carEntities[0]);
            return null;
        }
    }
}
