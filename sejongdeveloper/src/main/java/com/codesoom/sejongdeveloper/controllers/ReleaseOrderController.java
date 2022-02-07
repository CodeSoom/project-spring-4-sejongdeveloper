package com.codesoom.sejongdeveloper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 출고에 대한 요청을 관리한다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/release-orders")
public class ReleaseOrderController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save() {
        return null;
    }

}
