package com.ceiba.domain.repository;

import com.ceiba.domain.entity.Motorcycle;

import java.util.List;

public interface MotorcycleRepository {

    List<Motorcycle> getMotorcycles();

    void saveMotorcycle(Motorcycle motorcycle);

    void deleteMotorcycle(Motorcycle motorcycle);

    byte getCountMotorcycle();

}
