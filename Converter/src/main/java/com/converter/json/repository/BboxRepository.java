package com.converter.json.repository;

import com.converter.json.entity.Bbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BboxRepository extends JpaRepository<Bbox, Long> {
}
