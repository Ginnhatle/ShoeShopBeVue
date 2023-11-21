package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Size;
import org.datn.app.core.service.SizeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @PostMapping("/add")
    public Size addSize(@RequestBody Size size){
        return sizeService.doInsert(size);
    }
    @GetMapping("/get/{id}")
    public Size getSizeById(@PathVariable Long id){
        return sizeService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteSizeById(@PathVariable Long id){
        sizeService.doDeleteById(id);
    }
    @PutMapping("/update/{id}")
    public Size updateSizeById(@PathVariable Long id,@RequestBody Size size){
        return sizeService.doUpdateById(size,id);
    }
    @GetMapping("/findAll")
    public List<Size> getAllSize(){
        return sizeService.findAll();
    }
}
