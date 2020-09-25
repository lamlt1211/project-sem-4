package com.bkap.util;


import com.bkap.entity.CartInfo;

import javax.servlet.http.HttpServletRequest;

public class CartSupportUtils {

    private CartSupportUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static CartInfo getCartInSession(HttpServletRequest request) {

        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");


        if (cartInfo == null) {
            cartInfo = new CartInfo();

            request.getSession().setAttribute("myCart", cartInfo);
        }

        return cartInfo;
    }

    public static void removeCartInSession(HttpServletRequest request) {
        request.getSession().removeAttribute("myCart");
    }
}

