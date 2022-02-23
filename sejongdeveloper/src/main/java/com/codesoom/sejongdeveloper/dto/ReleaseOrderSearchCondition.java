package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class ReleaseOrderSearchCondition {
    private String name;    //출고명
}
