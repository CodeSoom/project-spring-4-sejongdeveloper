package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
