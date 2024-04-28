package com.zz.book.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zz.book.pojo.CartItem;
import com.zz.book.pojo.zzCart;
import com.zz.book.service.IZzCustomer;
import com.zz.book.service.IZzOrder;
import com.zz.common.dto.ZzBookDTO;
import com.zz.common.dto.ZzCustomerDTO;
import com.zz.common.dto.ZzOrderDTO;
import com.zz.common.dto.ZzOrderItemDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class zzCartController {

    private final IZzCustomer customerService;
    private final IZzOrder orderService;
    private final ModelMapper modelMapper;

    @PostMapping
    public String addBook(HttpSession session, @RequestBody ZzBookDTO book) {
        zzCart cart = getCartFromSession(session);
        cart.addBook(book);
        session.setAttribute("cart", cart);
        return "书籍已添加到购物车";
    }

    @DeleteMapping
    public String removeBook(HttpSession session, @RequestBody ZzBookDTO book) {
        zzCart cart = getCartFromSession(session);
        cart.removeBook(book);
        session.setAttribute("cart", cart);
        return "书籍已从购物车中移除";
    }

    private zzCart getCartFromSession(HttpSession session) {
        zzCart cart = (zzCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new zzCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping
    public Map<ZzBookDTO, CartItem> getCartItems(HttpSession session) {
        zzCart cart = getCartFromSession(session);
        return cart.getItems();
    }


    @PostMapping("/pay")
    public String pay(HttpSession session) {
        zzCart cart = getCartFromSession(session);
        BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice"); // 假设已经存储了总价

        if (totalPrice == null) {
            totalPrice = cart.getItems().values().stream()
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        Long customerId = StpUtil.getLoginIdAsLong();
        ZzCustomerDTO customerDTO = customerService.zzDetails(customerId);
        if (customerDTO.getZzBalance().compareTo(totalPrice) < 0) {
            return "余额不足";
        }
        customerDTO.setZzBalance(customerDTO.getZzBalance().subtract(totalPrice));
        customerDTO.setZzPassword(null);
        customerService.zzUpdate(customerDTO);

        ZzOrderDTO orderDTO = new ZzOrderDTO();
        orderDTO.setZzCustomerId(customerId);
        orderDTO.setZzOrderTotal(totalPrice);

        List<ZzOrderItemDTO> orderItems = cart.getItems().values().stream()
                .map(item -> {
                    ZzOrderItemDTO orderItemDTO = new ZzOrderItemDTO();
                    orderItemDTO.setZzBookId(item.getBook().getZzBookId());
                    orderItemDTO.setZzQuantity(item.getQuantity());
                    orderItemDTO.setZzPrice(item.getTotalPrice());
                    return orderItemDTO;
                })
                .collect(Collectors.toList());
        orderDTO.setZzOrderItems(orderItems);

        orderService.zzCreate(orderDTO);

        session.removeAttribute("cart");
        session.removeAttribute("totalPrice");
        return "支付成功";
    }
}