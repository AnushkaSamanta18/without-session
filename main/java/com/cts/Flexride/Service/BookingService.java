

package com.cts.Flexride.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.Flexride.Entity.BookingEntity;
import com.cts.Flexride.Entity.UserEntity;
import com.cts.Flexride.Entity.CarEntity;
import com.cts.Flexride.Repo.BookingRepo;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;
    
	/*
	 * public void bookCar(UserEntity user, CarEntity car) { BookingEntity booking =
	 * new BookingEntity(); booking.setUser(user); booking.setCar(car);
	 * bookingRepo.save(booking); }
	 */
    
    
    public void bookCar(UserEntity user, CarEntity car) {
    	if(user==null||car==null) {
    		System.out.println("Booking failed");
    	    return;
    }
        BookingEntity booking = new BookingEntity(user, car);
        bookingRepo.save(booking);
    }


    public List<BookingEntity> getAllBookings() {
        return bookingRepo.findAll();
    }
    
    
}

