package com.simon.service;

import com.simon.entity.Bike;
import com.simon.entity.Kayak;
import com.simon.entity.Tent;
import com.simon.repo.BikeRepo;
import com.simon.repo.KayakRepo;
import com.simon.repo.TentRepo;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private BikeRepo bikeRepo;
    private KayakRepo kayakRepo;
    private TentRepo tentRepo;

    public InventoryService() {
    }

    public InventoryService(BikeRepo bikeRepo, KayakRepo kayakRepo, TentRepo tentRepo) {
        this.bikeRepo  = bikeRepo;
        this.kayakRepo = kayakRepo;
        this.tentRepo  = tentRepo;
    }

    public <T> List<T> findAll( Class<T> type ) {

        if( type == Bike.class )
            return (List<T>) bikeRepo.findAll();

        else if(  type == Kayak.class )
            return (List<T>) kayakRepo.findAll();

        else if( type == Tent.class )
            return (List<T>) tentRepo.findAll();

        return null;
    }
}
