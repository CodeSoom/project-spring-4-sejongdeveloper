package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailResponse;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/release-order-details")
public class ReleaseOrderDetailController {

    private final ReleaseOrderDetailRepository releaseOrderDetailRepository;

    @GetMapping("/release-orders/{releaseOrderId}")
    public List<ReleaseOrderDetailResponse> list(@PathVariable Long releaseOrderId) {
        return releaseOrderDetailRepository.findAllByReleaseOrderId(releaseOrderId).stream()
                .map(ReleaseOrderDetailResponse::new)
                .collect(Collectors.toList());
    }

}
