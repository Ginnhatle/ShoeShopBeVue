package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Brand;
import org.datn.app.core.entity.extend.BrandResponse;
import org.datn.app.core.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/add")
    public ResponseEntity<?> addPublisher(@RequestBody Brand brand){
        List<String> errorList = new ArrayList<>();
        if(brand.getName() == null || brand.getName().trim().isEmpty()){
            errorList.add("Tên nhà xuất bản không được để trống");
        }
//        if(!brandService.findByName(brand.getName()).isEmpty()){
//            errorList.add("Tên nhà xuất bản đã tồn tại");
//        }
        if(errorList.size() > 0){
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(brandService.doInsert(brand));
    }

    @GetMapping("/get/{id}")
    public Brand getPublisherById(@PathVariable Long id){
        return brandService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePublisherById(@PathVariable Long id){
        brandService.doDeleteById(id);
    }
    @PutMapping("/update/{id}")
    public Brand updatePublisherById(@PathVariable Long id, @RequestBody Brand brand){
        return brandService.doUpdateById(brand,id);
    }
    @GetMapping("/page/{page}/{size}")
    public Page<Brand> getAllPublisher(@PathVariable int page, @PathVariable int size){
        return brandService.findAll(page,size);
    }

    @GetMapping("/findAll")
    public List<Brand> getAllPublisher(){
        return brandService.findAll();
    }

    @GetMapping("/findAllData")
    public List<BrandResponse> findAll(){
        return brandService.findAllData();
    }

    @GetMapping("/getByName/{name}")
    public Brand getPublisherByName(@PathVariable String name){
        return brandService.findByName(name);
    }
}
