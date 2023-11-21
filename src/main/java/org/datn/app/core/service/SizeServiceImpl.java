package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Size;
import org.datn.app.core.repo.SizeRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService{
    private final SizeRepo sizeRepo;
    @Override
    public Size doInsert(Size size) {
        return sizeRepo.save(size);
    }

    @Override
    public Size doUpdateById(Size size, Long aLong) {
        Size size1 = sizeRepo.findById(aLong).get();
        size1.setSize(size.getSize());
        return sizeRepo.save(size1);
    }

    @Override
    public Size doDeleteById(Long aLong) {
        sizeRepo.deleteById(aLong);
        return null;
    }

    @Override
    public Size findById(Long aLong) {
        return sizeRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Size> findAll() {
        return sizeRepo.findAll();
    }

    @Override
    public Page<Size> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return sizeRepo.findAll(pageable);
    }
}
