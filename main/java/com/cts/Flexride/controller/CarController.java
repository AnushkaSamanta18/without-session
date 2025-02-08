
package com.cts.Flexride.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.Flexride.Entity.BookingEntity;
import com.cts.Flexride.Entity.CarEntity;
import com.cts.Flexride.Entity.UserEntity;
import com.cts.Flexride.Service.BookingService;
import com.cts.Flexride.Service.CarService;
import com.cts.Flexride.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/car")
public class CarController {

	@Autowired
	private CarService carService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	// Show all cars in CarDetails page
	@GetMapping("/list")
	public String showCarDetailsPage(Model model, HttpSession session) {
		System.out.println("--> from list(model):" + model.getAttribute("errorMessage"));
		System.out.println("--> from list(session):" + session.getAttribute("errorMessage"));
		model.addAttribute("cars", carService.getAllCars());
		model.addAttribute("car", new CarEntity());
		return "Cardetails";
	}

	// Add or Update Car
	@PostMapping("/save")
	public String saveCar(@ModelAttribute("car") CarEntity car) {
		carService.saveCar(car);
		return "redirect:/car/list";
	}

	/*
	 * // Delete Car
	 * 
	 * @GetMapping("/delete/{id}") public String deleteCar(@PathVariable int id) {
	 * carService.deleteCar(id); return "redirect:/car/list"; }
	 */

	// CORRECT

	/*
	 * @GetMapping("/delete/{id}") public String deleteCar(@PathVariable int id,
	 * Model model, HttpSession session) { try { carService.deleteCar(id); } catch
	 * (RuntimeException e) { System.out.println(e.getMessage() + "<------------");
	 * model.addAttribute("errorMessage", e.getMessage());
	 * session.setAttribute("errorMessage", e.getMessage());
	 * model.addAttribute("cars", carService.getAllCars());
	 * System.out.println("--Inside the catch block of deleteCar"); //
	 * redirectAttributes.addFlashAttribute("errorMessage","Can't delete this car //
	 * because it is booked."); return "Cardetails"; } return "redirect:/car/list";
	 * }
	 */

	
	
	  //ADDING
	  
		@GetMapping("/delete/{id}") 
		public String deleteCar(@PathVariable int id,RedirectAttributes redirectAttributes) {
			try { 
				carService.deleteCar(id);
	  }catch(RuntimeException e) 
			{
	  System.out.println(e.getMessage()+"<------------");
	  redirectAttributes.addFlashAttribute("errorMessage","Cannot delete this car as it is booked");
	  //System.out.println("--Inside the catch block of deleteCar");
	  //redirectAttributes.addFlashAttribute("errorMessage","Can't delete this car because it is booked."); 
	  return "redirect:/car/list";
	  } 
			return "redirect:/car/list"; }
	  
	 

	// Edit Car (Load existing data into form)
	@GetMapping("/edit/{id}")
	public String editCar(@PathVariable int id, Model model) {
		CarEntity car = carService.getCarById(id);
		if (car != null) {
			model.addAttribute("car", car);
		} else {
			model.addAttribute("car", new CarEntity()); // Ensures form does not break
		}
		model.addAttribute("cars", carService.getAllCars());
		return "Cardetails"; // Loads the page with populated form data
	}

	@GetMapping("/booking")
	public String showAvailableCars(Model model, HttpSession session) {
		List<CarEntity> cars = carService.getAllCars();
		System.out.println(cars);
		model.addAttribute("carsList", cars);

		// Assuming user is stored in the session after login
		UserEntity user = (UserEntity) session.getAttribute("loggedInUser");
		if (user != null) {
			model.addAttribute("users", user);
		}

		return "book"; // Renders book.html
	}
/*
	// Book Car
   //correct
	@PostMapping("/book/{carId}")
	public String bookCar(@PathVariable int carId, @RequestParam int userId, HttpSession session, Model model) {

		CarEntity car = carService.getCarById(carId);
		UserEntity user = userService.getUserById((int) session.getAttribute("userId"));

		if (car != null && user != null) {
			bookingService.bookCar(user, car);
		}
		List<BookingEntity> bookings = bookingService.getAllBookings();
		session.setAttribute("bookings", bookings); //
		model.addAttribute("bookings", bookings);
		return "dashboard";
	}
	*/
	
	/*
	 * //i add this
	 * 
	 * @GetMapping("/dashboard") public String showDashboard(HttpSession session,
	 * Model model) { // Get logged-in user from session UserEntity user =
	 * (UserEntity) session.getAttribute("loggedInUser");
	 * 
	 * if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) { return
	 * "redirect:/car/booking"; // Redirect customers to the booking page }
	 * List<BookingEntity> bookings = bookingService.getAllBookings();
	 * model.addAttribute("bookings", bookings); return "dashboard"; }
	 */
	/*
	//Add
	@PostMapping("/book/{carId}")
	public String bookCar(@PathVariable int carId, @RequestParam int userId, HttpSession session, Model model) {
	    CarEntity car = carService.getCarById(carId);
	    UserEntity user = userService.getUserById((int) session.getAttribute("userId"));

	    if (car != null && user != null) {
	        bookingService.bookCar(user, car);
	        model.addAttribute("bookingSuccess", "Your car has been booked successfully!");
	    } else {
	        model.addAttribute("bookingError", "Booking failed. Please try again.");
	    }

	    // Stay on book.html and show a popup message
	    model.addAttribute("carsList", carService.getAllCars());
	    return "book";
	}
	*/
	
	
	
	/*
	@PostMapping("/book/{carId}")
	public String bookCar(@PathVariable int carId, HttpSession session, Model model) {
	    Integer userId = (Integer) session.getAttribute("userId");

	    if (userId == null) {
	        model.addAttribute("bookingError", "User not logged in.");
	        return "book"; // Stay on book.html
	    }

	    CarEntity car = carService.getCarById(carId);
	    UserEntity user = userService.getUserById(userId);

	    if (car != null && user != null) {
	        bookingService.bookCar(user, car);
	        model.addAttribute("bookingSuccess", "Your car has been booked successfully!");
	    } else {
	        model.addAttribute("bookingError", "Booking failed. Please try again.");
	    }

	    // Update available cars list
	    model.addAttribute("carsList", carService.getAllCars());

	    return "book"; // Stay on book.html
	}
	*/
	
	
	
	
	// Book a car and stay on book.html with a popup
    @PostMapping("/book/{carId}")
    public String bookCar(@PathVariable int carId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        CarEntity car = carService.getCarById(carId);
        UserEntity user = userService.getUserById(userId);

        if (car != null && user != null) {
            System.out.println("Booking car for User ID: " + userId + ", Car ID: " + carId);
            bookingService.bookCar(user, car);
            model.addAttribute("bookingSuccess", "Your car has been booked successfully!");
        } else {
            model.addAttribute("bookingError", "Booking failed. Please try again.");
        }

        // Refresh car list
        model.addAttribute("carsList", carService.getAllCars());
        return "book"; // Stay on book.html
    }
    

    // Show the admin dashboard with bookings (Only for admin)
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Get logged-in user from session
        UserEntity user = (UserEntity) session.getAttribute("loggedInUser");

        // Only allow admin to see dashboard
        if (user == null || !"anushka@gmail.com".equalsIgnoreCase(user.getEmail())) {
            return "redirect:/car/booking"; // Redirect normal users to the booking page
        }

        List<BookingEntity> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "dashboard"; // Only admin can see this
    }
}

