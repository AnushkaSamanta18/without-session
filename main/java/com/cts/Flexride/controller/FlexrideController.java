package com.cts.Flexride.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.Flexride.Entity.AdminEntity;
import com.cts.Flexride.Entity.BookingEntity;
import com.cts.Flexride.Service.AdminService;
import com.cts.Flexride.Service.BookingService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class FlexrideController {

	@Autowired
	private AdminService adminservice;

	@Autowired
	private BookingService bookingService;

	@GetMapping("/")
	public String home() {
		return "front";
	}

	// customerlogin page

	@GetMapping("/Login")
	public String Cuogin() {
		return "Login";
	}

	/* @GetMapping("/AdminLogin") public String Login() { return "AdminLogin"; } */

	/*
	 * @GetMapping("/signup") public String SignUp() { return "signup"; }
	 */
	@GetMapping("/index")
	public String Index() {
		return "index";
	}

	@GetMapping("/front")
	public String front() {
		return "front";
	}

	@GetMapping("/contact")
	public String Contact() {
		return "contact";
	}

	/*
	 * @GetMapping("/book") public String book() { return "book"; }
	 */

	/*
	 * @GetMapping("/dashboard") public String Dashboard() { return "dashboard"; }
	 */

	/*
	 * @GetMapping("/customerdashboard") public String CustomerDashboard() { return
	 * "customerdashboard"; }
	 */

	/*
	 * @GetMapping("/Cardetails") public String CarDetails() { return "Cardetails";
	 * }
	 */

	// Admin part
	@GetMapping("/AdminLogin")
	public String showAdminLoginPage(HttpSession httpSession, Model model, HttpServletResponse response) {
		httpSession.removeAttribute("error");

		// response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		// response.setHeader("Pragma", "no-cache");
		// response.setHeader("Expires", "0");

		return "AdminLogin";
	}

	@PostMapping("/AdminLogin")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model,
			HttpServletRequest request) {
		AdminEntity admin = adminservice.validateAdmin(email, password);

		if (admin != null & session != null) {
			session.setAttribute("name", admin.getName()); // Store admin name in session
			model.addAttribute("name", admin.getName());
			return "redirect:/dashboard"; // Redirect to update welcome message
		} else {
			model.addAttribute("error", "Wrong email or password");
			return "AdminLogin";
		}

	}
	/*
	 * @GetMapping("/dashboard") public String showDashboard() { return
	 * "dashboard";}
	 */

	// Adding
	@GetMapping("/dashboard")
	public String showDashboard(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		List<BookingEntity> bookings = bookingService.getAllBookings();
		session.setAttribute("bookings", bookings);
		model.addAttribute("bookings", bookings);
		System.out.println("session is new or not:"+session.isNew());
		if (!session.isNew()) {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");
			String cookieName = "";
			Cookie cookies[] = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookieName.equals("JSESSIONID")) {
					cookieName = cookie.getName();
					System.out.println("from dashboard:CookieName:" + cookieName);
				}
			}
			return "dashboard";
		}
		return "AdminLogin"; // Ensures Dashboard.html is displayed with booking table
	}

	// Logout Function
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		if (session != null) {
			// Remove JSESSIONID Cookie
			//Cookie cookie = new Cookie("JSESSIONID", null);
			//cookie.setHttpOnly(true);
			//cookie.setSecure(false); // Ensure it matches your app's security settings
			//cookie.setPath("/");
			//cookie.setMaxAge(100);
			//response.addCookie(cookie);
			// Prevent browser caching
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");
			System.out.println("remove session:" + request.getSession(false));
			session.invalidate();
			return "redirect:/";
			//System.out.println("remove session:" + request.getSession(false));
		}
		return "AdminLogin";

	}

}
