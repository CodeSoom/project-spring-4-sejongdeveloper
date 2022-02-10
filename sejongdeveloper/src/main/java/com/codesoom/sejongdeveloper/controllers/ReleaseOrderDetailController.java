package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/release-order-details")
public class ReleaseOrderDetailController {

    @GetMapping("/release-orders/{id}")
    public List<ReleaseOrderDetailResponse> list(@PathVariable Long id) {
        return null;
    }

}
