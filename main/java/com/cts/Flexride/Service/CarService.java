
package com.cts.Flexride.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cts.Flexride.Entity.CarEntity;
import com.cts.Flexride.Repo.BookingRepo;
import com.cts.Flexride.Repo.CarRepo;

@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;
    
    @Autowired
    private BookingRepo bookingRepo;

    // Create or Update Car
    public void saveCar(CarEntity car) {
        carRepo.save(car);
    }

    // Retrieve All Cars
    public List<CarEntity> getAllCars() {
        return carRepo.findAll();
    }

    // Delete Car
    public void deleteCar(int id) {
    	CarEntity car = carRepo.findById(id).orElse(null);
    	//boolean status=false;
    	if(car!=null) {
    		if(!bookingRepo.findByCar(car).isEmpty()) {
    			System.out.println("Car is book we cannot delete it");
    		//	status=true;
    			throw new RuntimeException("Car cannot be deleted as it has active bookings");
    		}
    	}
        carRepo.delete(car);
        //return status;
    }

    // Retrieve Car by ID
    public CarEntity getCarById(int id) {
        return carRepo.findById(id).orElse(null);
    }
}

