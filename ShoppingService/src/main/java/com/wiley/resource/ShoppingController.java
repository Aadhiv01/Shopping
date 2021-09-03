package com.wiley.resource;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.OrderItem;
import com.wiley.bean.Orders;
import com.wiley.bean.Product;
import com.wiley.bean.UserBean;
import com.wiley.service.ShoppingService;

@RestController
@SessionAttributes("user")
public class ShoppingController {
	
	@Autowired
	ShoppingService shoppingService;

	@GetMapping("/")
	public ModelAndView LoginPageController() {
		return new ModelAndView("Login","user",new UserBean());
	}
	
	@GetMapping("/SignUp")
	public ModelAndView newRedirect() {
		return new ModelAndView("SignUp","user",new UserBean());
	}
	
	@PostMapping("/login")
	public ModelAndView checkUser(@Valid @ModelAttribute("user") UserBean user, BindingResult result, HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		if(result.hasErrors())
			return new ModelAndView("Login","user",new UserBean());
		if(!shoppingService.checkUser(user.getEmail(), false)) {
			System.out.println("Email doesn't exist");
			result.rejectValue("email","error.email","User doesn't exist");
			return new ModelAndView("Login","user",new UserBean());
		}
		if(!(shoppingService.fetchUser(user.getEmail()).getPassword().equals(user.getPassword()))){
			System.out.println("Wrong password");
			result.rejectValue("password","error.pass","Incorrect Password");
			return new ModelAndView("Login","user",new UserBean());
			
		}
		user = shoppingService.fetchUser(user.getEmail());
		System.out.println(user);
		session.removeAttribute("user");
		session.setAttribute("user", user);
		modelAndView.addObject("user", user);
		modelAndView.addObject("orderitem", new OrderItem());
		modelAndView.addObject("products", shoppingService.getAllProducts());
		List<Long> ids = new ArrayList<>();
		for(Product id : shoppingService.getAllProducts())
			ids.add(id.getId());
		modelAndView.addObject("productIds", ids);
		modelAndView.setViewName("MainPage");
		return modelAndView;
	}
	
	@PostMapping("/save")
	public ModelAndView insertUser(@ModelAttribute("user") UserBean user, BindingResult result, HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		if(result.hasErrors())
			return new ModelAndView("SignUp","user",new UserBean());
		if(shoppingService.checkUser(user.getEmail(), true)) {
			result.rejectValue("email","error.email","Email Exists");
			return new ModelAndView("SignUp","user",new UserBean());
		}
		shoppingService.insertUser(user);
		session.removeAttribute("user");
		session.setAttribute("user", user);
		modelAndView.addObject("user",user);
		System.out.println("Products : \n" + shoppingService.getAllProducts());
		modelAndView.addObject("products", shoppingService.getAllProducts());
		modelAndView.addObject("orderitem", new OrderItem());
		List<Long> ids = new ArrayList<>();
		for(Product id : shoppingService.getAllProducts())
			ids.add(id.getId());
		modelAndView.addObject("productIds", ids);
		modelAndView.setViewName("MainPage");
		return modelAndView;
	}
	
	@GetMapping("/main")
	public ModelAndView MainPage(HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("user", ((UserBean)session.getAttribute("user")));
		modelAndView.addObject("orderitem", new OrderItem());
		modelAndView.addObject("products", shoppingService.getAllProducts());
		List<Long> ids = new ArrayList<>();
		for(Product id : shoppingService.getAllProducts())
			ids.add(id.getId());
		modelAndView.addObject("productIds", ids);
		modelAndView.setViewName("MainPage");
		return modelAndView;
	}
	
	@PostMapping("/purchase")
	public ModelAndView addPurchase(@ModelAttribute("orderitem")OrderItem item,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(item.getQuantity() < 0) {
			mv.setViewName("badRequest");
			mv.addObject("id",item.getProductId());
			mv.addObject("quantity",item.getQuantity());
			return mv;
		}
		List<OrderItem> items = (List<OrderItem>)session.getAttribute("items");
		if(items == null)
			items = new ArrayList<>();
		mv.setViewName("Proceed");
		session.removeAttribute("items");
		Optional<OrderItem> existingItem = items.stream().filter(e->e.getProductId().equals(item.getProductId())).findAny();
		if(existingItem.isPresent()) {
			existingItem.get().setQuantity(existingItem.get().getQuantity()+item.getQuantity());
			session.setAttribute("items", items);
			mv.addObject("items", items);
			return mv;
		}
		Optional<Product> product = shoppingService.getAllProducts().stream().filter(e->e.getId()==item.getProductId()).findAny();
		Optional<InventoryItem> inventoryItem = shoppingService.getAllItems().stream().filter(e->e.getProductCode().equals(product.get().getCode())).findAny();
		if(inventoryItem.isEmpty() || item.getQuantity() > inventoryItem.get().getAvailableQuantity()) {
			System.out.println("Item with code " + product.get().getCode() + " quantity insufficient");
			mv.setViewName("badRequest");
			mv.addObject("id",product.get().getId());
			mv.addObject("quantity",item.getQuantity());
			return mv;
		}
		item.setProductPrice(new BigDecimal(product.get().getPrice()));
		System.out.println(item);
		items.add(item);
		session.setAttribute("items", items);
		Double total = (Double)session.getAttribute("total");
		if(total == null)
			total = 0D;
		session.removeAttribute("total");
		total += item.getQuantity()*product.get().getPrice();
		System.out.println(total);
		session.setAttribute("total", total);
		mv.addObject("items", items);
		mv.setStatus(HttpStatus.CREATED);
		return mv;
	}
	
	@GetMapping("/proceed")
	public ModelAndView Proceed(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		List<OrderItem> items = (List<OrderItem>)session.getAttribute("items");
		mv.addObject("items", items);
		mv.setViewName("Proceed");
		return mv;
		
	}
	
	@GetMapping("/end")
	public ModelAndView addPurchase(HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("user", ((UserBean)session.getAttribute("user")));
		List<OrderItem> items = (List<OrderItem>)session.getAttribute("items");
		modelAndView.addObject("items", items);
		modelAndView.addObject("total", (Double)session.getAttribute("total"));
		modelAndView.setViewName("Finalised");
		return modelAndView;
	}
	
	@GetMapping("/order")
	public ModelAndView finalisedPurchase(HttpSession session) {
		Orders order = new Orders();
		order.setCustomerEmail(((UserBean)session.getAttribute("user")).getEmail());
		order.setCustomerAddress(((UserBean)session.getAttribute("user")).getAddress());
		ModelAndView modelAndView=new ModelAndView();
		order.setItems((List<OrderItem>)session.getAttribute("items"));
		System.out.println(order);
		modelAndView.addObject("user", ((UserBean)session.getAttribute("user")));
		order = shoppingService.createOrder(order);
		if(order == null) {
			modelAndView.setStatus(HttpStatus.BAD_REQUEST);
			modelAndView.setViewName("Details");
		}
		modelAndView.addObject("order", order);
		modelAndView.addObject("items", order.getItems());
		modelAndView.addObject("total", (Double)session.getAttribute("total"));
		modelAndView.setViewName("Details");
		modelAndView.setStatus(HttpStatus.CREATED);
		return modelAndView;
	}
}
