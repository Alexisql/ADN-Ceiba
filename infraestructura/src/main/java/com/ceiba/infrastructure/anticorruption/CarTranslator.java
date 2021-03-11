package com.ceiba.infrastructure.anticorruption;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.infrastructure.db.entity.CarEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CarTranslator {

    public static Carro fromCarDBToCarDomain(CarEntity carEntity) throws ParseException {
        Carro carro = new Carro(carEntity.plate, carEntity.type);
        Calendar fechaIngreso = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaIngreso.setTime(sdf.parse(String.valueOf(fechaIngreso)));
        carro.setFechaIngreso(fechaIngreso);
        return carro;
    }

    public static CarEntity fromCarDomainToCarBD(Carro carro) {
        CarEntity carEntity = new CarEntity();
        carEntity.setPlate(carro.getPlaca());
        carEntity.setType(carro.getTipo());
        carEntity.setDateEntry(carro.getFechaIngreso().toString());
        return carEntity;
    }

    public static List<Carro> listCarsFromEntityToEntityDB(List<CarEntity> carList) throws ParseException {
        List<Carro> carroListTmp = new ArrayList<>();
        for (CarEntity carEntity : carList) {
            carroListTmp.add(fromCarDBToCarDomain(carEntity));
        }
        return carroListTmp;
    }
}
