package com.vermeg.bookstore.service;

import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.entities.OrderItemKey;
import com.vermeg.bookstore.exception.OrderItemListEmptyException;
import com.vermeg.bookstore.exception.OrderItemNotFoundException;
import com.vermeg.bookstore.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


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

    public OrderItem createOrderItem(OrderItem orderItem) {
        OrderItemKey id = new OrderItemKey(orderItem.getId().getOrderId(),orderItem.getId().getBookId());

        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(OrderItemKey orderItemId, OrderItem orderItem) throws OrderItemNotFoundException {
        OrderItem _orderItem = this.getOrderItemById(orderItemId.getOrderId(),orderItemId.getBookId());

        _orderItem.setBook(orderItem.getBook());
        _orderItem.setOrder(orderItem.getOrder());
        _orderItem.setPrice(orderItem.getPrice());
        _orderItem.setQuantity(orderItem.getQuantity());

        return orderItemRepository.save(_orderItem);
    }

    public void deleteOrderItem(OrderItemKey orderItemId) throws OrderItemNotFoundException {
        OrderItem _orderItem = this.getOrderItemById(orderItemId.getOrderId(),orderItemId.getBookId());

        orderItemRepository.delete(_orderItem);
    }
}
