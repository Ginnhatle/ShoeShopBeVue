package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Rank;
import org.datn.app.core.entity.Size;
import org.datn.app.core.service.RankService;
import org.datn.app.core.service.SizeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor

public class RankController {
    private final RankService rankService;

    @PostMapping("/add")
    public Rank addRank(@RequestBody Rank rank){
        return rankService.doInsert(rank);
    }
    @GetMapping("/get/{id}")
    public Rank getRankById(@PathVariable Long id){
        return rankService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteRankById(@PathVariable Long id){
        rankService.doDeleteById(id);
    }
    @PutMapping("/update/{id}")
    public Rank updateRankById(@PathVariable Long id,@RequestBody Rank rank){
        return rankService.doUpdateById(rank,id);
    }
    @GetMapping("/findAll")
    public List<Rank> getAllSize(){
        return rankService.findAll();
    }
}
