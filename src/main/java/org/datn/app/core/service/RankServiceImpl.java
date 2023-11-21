package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.RankDTO;
import org.datn.app.core.entity.Rank;
import org.datn.app.core.repo.RankRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class RankServiceImpl implements RankService{
    private final RankRepo rankRepo;
    @Override
    public Rank doInsert(Rank rank) {
        return rankRepo.save(rank);
    }

    @Override
    public Rank doUpdateById(Rank rank, Long aLong) {
        //update
        Rank rank1 = rankRepo.findById(aLong).get();
        rank1.setCondition(rank.getCondition());
        rank1.setDiscount(rank.getDiscount());
        rank1.setTargetMoney(rank.getTargetMoney());
        rank1.setName(rank.getName());
        return rankRepo.save(rank1);
    }

    @Override
    public Rank doDeleteById(Long aLong) {
        rankRepo.deleteById(aLong);
        return null;
    }

    @Override
    public Rank findById(Long aLong) {
        return rankRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Rank> findAll() {
        return rankRepo.findAll();
    }

    @Override
    public Page<Rank> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return rankRepo.findAll(pageable);
    }

    @Override
    public Page<RankDTO> findAllRank(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<?> addRank(Rank rank) {
        return null;
    }

    @Override
    public RankDTO findRankByID(Long id) {
        return null;
    }

    @Override
    public void deleteRank(Long id) {

    }

    @Override
    public ResponseEntity<?> updateRank(Rank rank) {
        return null;
    }
}
