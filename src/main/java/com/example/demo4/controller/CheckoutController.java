package com.example.demo4.controller;

import com.example.demo4.model.CartItem;
import com.example.demo4.model.Order;
import com.example.demo4.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @SuppressWarnings("unchecked")
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = orderService.checkout(cart);

        session.setAttribute("lastOrder", order);
        session.setAttribute("cart", new ArrayList<>());

        return "redirect:/checkout/success";
    }
}