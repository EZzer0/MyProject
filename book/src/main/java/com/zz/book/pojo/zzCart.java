package com.zz.book.pojo;

import com.zz.common.dto.ZzBookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class zzCart {
    private Map<ZzBookDTO, CartItem> items = new HashMap<>();

    public void addBook(ZzBookDTO book) {
        CartItem item = items.getOrDefault(book, new CartItem(book));
        item.setQuantity(item.getQuantity() + 1);
        item.setTotalPrice(book.getZzPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        items.put(book, item);
    }

    public void removeBook(ZzBookDTO book) {
        CartItem item = items.get(book);
        if (item != null) {
            item.setQuantity(item.getQuantity() - 1);
            if (item.getQuantity() > 0) {
                item.setTotalPrice(book.getZzPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                items.put(book, item);
            } else {
                items.remove(book);
            }
        }
    }

    public Map<ZzBookDTO, CartItem> getItems() {
        return items;
    }
}