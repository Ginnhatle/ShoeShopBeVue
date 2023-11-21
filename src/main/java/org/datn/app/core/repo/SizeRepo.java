package org.datn.app.core.repo;

import org.datn.app.core.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SizeRepo extends JpaRepository<Size,Long> {
    @Query("select s from Size s where s.size in ?1")
    List<Size> findAllBySize(List<String> sizeList);

    Size findBySize(String size);
}
