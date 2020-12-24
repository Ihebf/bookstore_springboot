package com.vermeg.bookstore.service;

import com.vermeg.bookstore.entities.*;
import com.vermeg.bookstore.exception.*;
import com.vermeg.bookstore.repository.BookRepository;
import com.vermeg.bookstore.repository.OrderItemRepository;
import com.vermeg.bookstore.repository.OrderRepository;
import com.vermeg.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
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


    public OrderItem getOrderItemById(Integer orderItemId, Integer bookId) throws OrderItemNotFoundException {
        OrderItemKey id = new OrderItemKey(orderItemId,bookId);
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(()->
                new OrderItemNotFoundException(orderItemId.toString()));
        return orderItem;
    }

    @Transactional
    public OrderItem createOrderItem(Integer orderId,Integer bookId,Integer userId)
            throws UserNotFoundException, BookNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException("Book not found"));
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            Order newOrder = new Order();
            OrderItem orderItem = new OrderItem(null,1,book.getPrice(),book,newOrder);
            List<OrderItem> orderItemList = new ArrayList<>();

            orderItemList.add(orderItem);

            newOrder.setUser(user);
            newOrder.setOrderItem(orderItemList);
            newOrder.setTotalPrice(orderItem.getPrice());

            orderRepository.save(newOrder);
            orderItemRepository.save(orderItem);
            return orderItem;
        }
        List<OrderItem> orderItemList = order.get().getOrderItem();
        OrderItem orderItem = null;
        for (OrderItem item : orderItemList) {
            if (item.getBook() == book) {
                item.setQuantity(item.getQuantity() + 1);
                orderItem = item;
                break;
            }
        }
        double totalPrice =
                orderItemList
                        .stream()
                        .mapToDouble(oItem -> oItem.getPrice() * oItem.getQuantity()).sum();
        order.get().setTotalPrice(totalPrice);

        orderRepository.save(order.get());
        orderItemRepository.save(orderItem);
        return orderItem;
    }

    public OrderItem updateOrderItem(Integer orderId, Integer bookId, OrderItem orderItem)
            throws OrderItemNotFoundException {

        OrderItem _orderItem = this.getOrderItemById(orderId,bookId);

        _orderItem.setBook(orderItem.getBook());
        _orderItem.setOrder(orderItem.getOrder());
        _orderItem.setPrice(orderItem.getPrice());
        _orderItem.setQuantity(orderItem.getQuantity());

        return orderItemRepository.save(_orderItem);
    }

    public OrderItem deleteOrderItem(Integer orderId, Integer bookId) throws OrderItemNotFoundException {
        OrderItem orderItem = this.getOrderItemById(orderId,bookId);

        orderItemRepository.delete(orderItem);
        return orderItem;
    }

}
