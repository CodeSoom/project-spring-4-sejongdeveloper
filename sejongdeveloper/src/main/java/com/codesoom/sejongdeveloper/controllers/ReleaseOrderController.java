package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ReleaseOrderService;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderResponse;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.errors.ReleaseOrderNoutFoundException;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 출고에 대한 요청을 관리한다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/release-orders")
public class ReleaseOrderController {

    private final ReleaseOrderService releaseOrderService;
    private final ReleaseOrderRepository releaseOrderRepository;

    /**
     * 출고를 저장하고 저장된 출고 일련번호를 리턴한다.
     *
     * @param request 저장할 출고
     * @return 저장된 출고 일련번호
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody @Valid ReleaseOrderSaveRequest request) {
        return releaseOrderService.saveReleaseOrder(request);
    }

    /**
     * 주어진 아이디의 출고를 리턴한다.
     *
     * @param id 출고의 아이디
     * @return 주어진 아이디의 출고
     */
    @GetMapping("{id}")
    public ReleaseOrderResponse detail(@PathVariable Long id) {
        return new ReleaseOrderResponse(getReleaseOrder(id));
    }

    /**
     * 주어진 아이디의 출고를 리턴한다.
     *
     * @param id 출고의 아이디
     * @return 주어진 아이디의 출고
     * @throws ReleaseOrderNoutFoundException 주어진 아이디의 출고를 찾지 못한 경우
     */
    private ReleaseOrder getReleaseOrder(Long id) {
        return releaseOrderRepository.findById(id)
                .orElseThrow(() -> new ReleaseOrderNoutFoundException(id));
    }
}
