package org.datn.app.core.repo;

import org.datn.app.core.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepo  extends JpaRepository<Color,Long> {
    Color findByName(String name);
}
