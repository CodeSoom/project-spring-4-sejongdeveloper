package com.codesoom.sejongdeveloper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 페이지 요청에 대하여 관리한다.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/page")
public class PageController {

    /**
     * 수주목록 페이지로 이동한다.
     *
     * @return 수주목록 페이지
     */
    @GetMapping("/obtain-orders")
    public String getObtainOrders() {
        return "obtainOrders/obtainOrders";
    }

    /**
     * 수주상세 페이지로 이동한다.
     *
     * @return 수주상세 페이지
     */
    @GetMapping("/obtain-order")
    public String obtainOrder() {
        return "obtainOrders/obtainOrder";
    }
}
