package com.ceiba.infrastructure.repository;

import com.ceiba.domain.entity.Motorcycle;
import com.ceiba.domain.repository.MotorcycleRepository;

import java.util.List;

public class MotorcycleRepositoryDB implements MotorcycleRepository {



    @Override
    public List<Motorcycle> getMotorcycles() {
        return null;
    }

    @Override
    public void saveMotorcycle(Motorcycle motorcycle) {

    }

    @Override
    public void deleteMotorcycle(Motorcycle motorcycle) {

    }

    @Override
    public byte getCountMotorcycle() {
        return 0;
    }
}
