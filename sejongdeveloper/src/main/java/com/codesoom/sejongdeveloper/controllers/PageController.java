package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ObtainOrderService;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * 페이지 요청에 대하여 관리한다.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/page")
public class PageController {

    private final ItemRepository itemRepository;
    private final ObtainOrderService obtainOrderService;

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
    @GetMapping({"/obtain-order", "/obtain-order/{id}"})
    public String obtainOrder(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("obtainOrderId", id);
        model.addAttribute("items", itemRepository.findAll());

        return "obtainOrders/obtainOrder";
    }

    /**
     * 출고상세 페이지로 이동한다.
     *
     * @return 출고상세 페이지
     */
    @GetMapping("/release-order")
    public String releaseOrder(@RequestParam(required = false) Long obtainOrderId, Model model) {
        if (obtainOrderId != null) {
            model.addAttribute("obtainOrder", obtainOrderService.findObtainOrder(obtainOrderId));
            model.addAttribute("today", LocalDate.now());
        }

        return "releaseOrders/releaseOrder";
    }
}
