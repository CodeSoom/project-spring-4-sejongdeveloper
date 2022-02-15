package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderDetailService {

    private final PlaceOrderDetailRepository placeOrderDetailRepository;

}
