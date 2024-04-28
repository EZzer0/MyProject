package com.zz.book.pojo;

import com.zz.common.dto.ZzBookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartItem {
    private ZzBookDTO book;
    private Integer quantity;
    private BigDecimal totalPrice;

    public CartItem(ZzBookDTO book) {
        this.book = book;
        this.quantity = 0;
        this.totalPrice = BigDecimal.ZERO;
    }
}