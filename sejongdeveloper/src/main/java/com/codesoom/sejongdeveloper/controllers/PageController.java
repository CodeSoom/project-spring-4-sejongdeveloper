package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 페이지 요청에 대하여 관리한다.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/page")
public class PageController {

    private final ItemRepository itemRepository;

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
    public String obtainOrder(Model model) {
        model.addAttribute("items", itemRepository.findAll());

        return "obtainOrders/obtainOrder";
    }
}
