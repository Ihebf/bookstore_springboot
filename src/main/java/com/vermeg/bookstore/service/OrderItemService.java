package com.vermeg.bookstore.service;

import com.vermeg.bookstore.entities.*;
import com.vermeg.bookstore.exception.*;
import com.vermeg.bookstore.repository.BookRepository;
import com.vermeg.bookstore.repository.OrderItemRepository;
import com.vermeg.bookstore.repository.OrderRepository;
import com.vermeg.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<OrderItem> getAllOrderItem() throws OrderItemListEmptyException {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        if(orderItems.isEmpty())
            throw new OrderItemListEmptyException("There is no orderItem");
        return orderItems;
    }


    public OrderItem getOrderItemById(Integer orderItemId, Integer bookId,String username) throws OrderNotFoundException, BookNotFoundException, OrderItemNotFoundException {
        Order order = orderRepository.findById(orderItemId).orElseThrow(()-> new OrderNotFoundException("order not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException("book not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " Not found"));

        if(order.getUser() == user) {
            OrderItem orderItem = orderItemRepository.findByOrderAndBook(order, book)
                    .orElseThrow(() ->
                            new OrderItemNotFoundException(orderItemId.toString()));
            return orderItem;
        }
        return null;
    }

    @Transactional
    public OrderItem createOrderItem(Integer bookId,String username) throws UserNotFoundException, BookNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException("Book not found"));

        if(user.getOrder() == null){
            Order order = new Order();
            OrderItem orderItem = new OrderItem();
            List<OrderItem> orderItemList = new ArrayList<>();

            orderItem.setBook(book);
            orderItem.setPrice(book.getPrice());
            orderItem.setQuantity(1);
            orderItem.setOrder(order);

            orderItemList.add(orderItem);

            order.setUser(user);
            order.setTotalPrice(book.getPrice());
            order.setState(false);
            order.setOrderItem(orderItemList);

            user.setOrder(order);

            orderItemRepository.save(orderItem);
            userRepository.save(user);
            orderRepository.save(order);


            return orderItem;
        }
        Order order = user.getOrder();
        List<OrderItem> orderItemList = order.getOrderItem();
        for (int i=0;i<orderItemList.size();i++){
            OrderItem item = orderItemList.get(i);
            if (item.getBook() == book){
                item.setQuantity(item.getQuantity()+1);
                orderItemRepository.save(item);
                calculateTotalPrice(order);
                return item;
            }
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(1);
        orderItem.setPrice(book.getPrice());
        orderItem.setBook(book);

        orderItemList.add(orderItem);
        orderItemRepository.save(orderItem);
        calculateTotalPrice(order);
        return orderItem;

    }

    @Transactional
    public void calculateTotalPrice(Order order) {
        int totalPrice = 0;
        for (int i=0;i<order.getOrderItem().size();i++){
            totalPrice+= order.getOrderItem().get(i).getPrice() * order.getOrderItem().get(i).getQuantity();
        }
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
    }

    public OrderItem updateOrderItem(Integer orderId, Integer bookId, OrderItem orderItem) throws OrderItemNotFoundException, OrderNotFoundException, BookNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("order not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException("book not found"));
        OrderItem orderI = orderItemRepository.findByOrderAndBook(order,book).orElseThrow(()-> new OrderItemNotFoundException("item not found"));

        orderI.setQuantity(orderItem.getQuantity());
        orderI.setOrder(orderItem.getOrder());
        orderI.setPrice(orderItem.getPrice());
        orderI.setBook(orderItem.getBook());

        return orderItemRepository.save(orderI);
    }

    public OrderItem deleteOrderItem(Integer orderItemId) throws OrderItemNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()-> new OrderItemNotFoundException("item not found"));
        Order order = orderItem.getOrder();
        List<OrderItem> orderItemList = order.getOrderItem();
        for(int i=0;i<orderItemList.size();i++){
            OrderItem item = orderItemList.get(i);
            if(item == orderItem){
                orderItemList.remove(i);
                calculateTotalPrice(order);
            }
        }
        orderItemRepository.delete(orderItem);
        return orderItem;
    }

}
