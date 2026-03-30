package com.example.demo4.controller;

import com.example.demo4.model.CartItem;
import com.example.demo4.model.Product;
import com.example.demo4.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        List<CartItem> cart = getCart(session);
        Product product = productService.getProductById(productId);

        if (product != null) {
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(product, quantity));
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);
        double total = cart.stream().mapToDouble(CartItem::getSubtotal).sum();

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Long productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        List<CartItem> cart = getCart(session);

        if (quantity <= 0) {
            cart.removeIf(item -> item.getProduct().getId().equals(productId));
        } else {
            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProduct().getId().equals(productId));
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }
}