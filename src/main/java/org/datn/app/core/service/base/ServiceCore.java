package org.datn.app.core.service.base;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface ServiceCore <T extends Serializable,ID extends Long> {
    T doInsert(T t);
    T doUpdateById(T t,ID id);
    T doDeleteById(ID id);
    T findById(ID id);
    List<T> findAll();
    Page<T> findAll(Integer page, Integer size);
}
