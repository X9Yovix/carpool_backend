package com.tekup.carpool_backend.service.Ride;

import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.repository.Ride.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImp implements RideService {
    private final RideRepository repository;

    public RideServiceImp(RideRepository repository) {
        this.repository=repository;
    }
    @Override
    public Ride save(Ride r) {
        return repository.save(r);
    }

    @Override
    public List<Ride> findAllRides() {
        return repository.findAll();
    }

    @Override
    public List<Ride> findByDep(String dep) {
        return repository.findByDep(dep);
    }

    @Override
    public List<Ride> findByArr(String arr) {
        return repository.findByArr(arr);
    }

    @Override
    public List<Ride> findByTime(String time) {
        return repository.findByTime(time);
    }

    @Override
    public Ride update(Ride r) {
            return repository.save(r);
        }

        @Override
        public void delete ( int idR){
            repository.deleteById(idR);
        }

    }