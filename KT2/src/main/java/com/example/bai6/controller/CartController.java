package com.example.bai6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.bai6.service.CartService;
import com.example.bai6.service.OrderService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/list";
    }

    @PostMapping("/add/{id}")
public String addToCart(@PathVariable Long id) {
    cartService.addToCart(id);
    return "redirect:/cart";
}

@PostMapping("/update")
public String updateQuantity(@RequestParam Long productId,
                             @RequestParam int quantity) {
    cartService.updateQuantity(productId, quantity);
    return "redirect:/cart";
}

@GetMapping("/remove/{id}")
public String removeFromCart(@PathVariable Long id) {
    cartService.removeFromCart(id);
    return "redirect:/cart";
}

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
    @PostMapping("/order")
    public String checkout() {

    orderService.checkout(cartService.getItems());

    cartService.clear();

    return "redirect:/products";
}
}
