package com.codesoom.sejongdeveloper.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime saveDateTime; //저장일시

    private LocalDateTime updateDateTime;   //수정일시

    @PrePersist
    private void prePersist() {
        saveDateTime = LocalDateTime.now();
    }

    @PostPersist
    private void postPersist() {
        updateDateTime = LocalDateTime.now();
    }
}
