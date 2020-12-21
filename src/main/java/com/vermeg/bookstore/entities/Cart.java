package com.vermeg.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
public class Cart {

    private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();
    private double price;

    public void add(Book book){
        CartItem item = map.get(book.getId());
        if(item == null){
            item = new CartItem();
            item.setBook(book);
            item.setQuantity(1);
            map.put(book.getId(),item);
        }else {
            item.setQuantity(item.getQuantity()+1);
        }
    }

    public double getPrice(){
        double totalPrice = 0;
        for(Map.Entry<Integer, CartItem> m: map.entrySet()){
            CartItem item = m.getValue();
            totalPrice += item.getPrice();
        }
        this.price = totalPrice;
        return price;
    }


}
