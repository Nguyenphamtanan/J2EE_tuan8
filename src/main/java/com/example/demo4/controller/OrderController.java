package com.example.demo4.controller;

import com.example.demo4.model.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/checkout/success")
    public String checkoutSuccess(HttpSession session, Model model) {
        Order order = (Order) session.getAttribute("lastOrder");
        model.addAttribute("order", order);
        return "checkout_success";
    }
}