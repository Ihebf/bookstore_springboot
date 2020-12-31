package com.vermeg.bookstore.service;

import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.exception.OrderListEmptyException;
import com.vermeg.bookstore.exception.OrderNotFoundException;
import com.vermeg.bookstore.exception.UserNotFoundException;
import com.vermeg.bookstore.repository.OrderRepository;
import com.vermeg.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

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

       // _order.setOrderItem(order.getOrderItem());
        _order.setState(order.isState());
        _order.setUser(order.getUser());

        return orderRepository.save(_order);
    }

    public Order deleteOrder(Integer orderId) throws OrderNotFoundException {
        Order order = this.getOrderById(orderId);
        orderRepository.delete(order);
        return order;
    }


    public List<OrderItem> getOrderItems(String username) throws UserNotFoundException, OrderNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user not found"));
        if(user.getOrder() == null)
            throw new OrderNotFoundException("There is no order");
        return user.getOrder().getOrderItem();
    }

    public Double getTotalPrice(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));

        if (user.getOrder() == null)
            return 0D;
        return user.getOrder().getTotalPrice();
    }
}
