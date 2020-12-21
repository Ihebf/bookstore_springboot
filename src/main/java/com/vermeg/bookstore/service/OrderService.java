package com.vermeg.bookstore.service;

import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.exception.OrderListEmptyException;
import com.vermeg.bookstore.exception.OrderNotFoundException;
import com.vermeg.bookstore.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrder() throws OrderListEmptyException {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty())
            throw new OrderListEmptyException("There is no book");
        return orders;
    }

    public Order getOrderById(Integer orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new OrderNotFoundException(orderId.toString()));
        return order;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer orderId, Order order) throws OrderNotFoundException {
        Order _order = this.getOrderById(orderId);

        _order.setPrice(order.getPrice());
        _order.setOrderItemSet(order.getOrderItemSet());
        _order.setState(order.isState());
        _order.setUser(order.getUser());

        return orderRepository.save(_order);
    }

    public void deleteOrder(Integer orderId) throws OrderNotFoundException {
        Order order = this.getOrderById(orderId);
        orderRepository.delete(order);
    }


    public Set<OrderItem> getOrderItems(Integer orderId) throws OrderNotFoundException{
        Order order = this.getOrderById(orderId);

        return order.getOrderItemSet();
    }
}
