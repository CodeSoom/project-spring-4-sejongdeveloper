package com.codesoom.sejongdeveloper.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderQueryService 클래스")
class ReleaseOrderQueryServiceTest {

    @Autowired
    private ReleaseOrderQueryService releaseOrderQueryService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class search_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_파라미터인_경우 {
            @Test
            @DisplayName("출고 목록을 리턴한다")
            void 출고_목록을_리턴한다() {

            }
        }
    }

}