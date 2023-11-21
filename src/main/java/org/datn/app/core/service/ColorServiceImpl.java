package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Color;
import org.datn.app.core.repo.ColorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService{
    private final ColorRepo colorRepo;
    @Override
    public List<Color> findAll() {
        return colorRepo.findAll();
    }
}
