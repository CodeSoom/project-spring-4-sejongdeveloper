package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderQueryService {

    private final PlaceOrderRepository placeOrderRepository;

    public Page<PlaceOrderResponse> search(PlaceOrderSearchCondition condition) {
        QueryResults<PlaceOrder> queryResults = placeOrderRepository.findAll(condition);

        List<PlaceOrderResponse> content = queryResults.getResults().stream()
                .map(entity -> PlaceOrderResponse.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .date(entity.getDate())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(content, condition.getPageable(), queryResults.getTotal());
    }

}
