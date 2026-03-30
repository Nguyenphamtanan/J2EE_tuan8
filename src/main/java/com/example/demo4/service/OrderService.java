package com.example.demo4.service;

import com.example.demo4.model.CartItem;
import com.example.demo4.model.Order;
import com.example.demo4.model.OrderDetail;
import com.example.demo4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order checkout(List<CartItem> cartItems) {
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(cartItem.getProduct());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(cartItem.getProduct().getPrice());

            double subtotal = cartItem.getProduct().getPrice() * cartItem.getQuantity();
            detail.setSubtotal(subtotal);

            total += subtotal;
            orderDetails.add(detail);
        }

        order.setOrderDetails(orderDetails);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }
}