package org.datn.app.core.service;

import org.datn.app.core.dto.RankDTO;
import org.datn.app.core.entity.Rank;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.entity.extend.VoucherResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface RankService extends ServiceCore<Rank,Long> {
    Page<RankDTO> findAllRank(Integer page, Integer size);
    ResponseEntity<?> addRank(Rank rank);
    RankDTO findRankByID(Long id);
    void deleteRank(Long id);
    ResponseEntity<?> updateRank(Rank rank);
}
