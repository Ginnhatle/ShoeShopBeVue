package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.AttributeConstant;
import org.datn.app.core.dto.AttributeAddAllRequest;
import org.datn.app.core.dto.DeleteAttributeRequest;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.service.AttributeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attribute")
@RequiredArgsConstructor
public class AttributeController {
    private final AttributeService attributeService;

    @PostMapping("/add")
    public ResponseEntity<?> saveAttribute(@RequestBody Attribute attribute){
        List<String> errorList = new ArrayList<>();
        if(attribute.getName().isEmpty()){
            errorList.add("Tên thuộc tính không được để trống");
        }
        if(attribute.getType().trim().isEmpty() || attribute.getType() == null ){
            errorList.add("Loại thuộc tính không được để trống");
        }
        if(!(attribute.getType().equals(AttributeConstant.STRING)||attribute.getType().equals(AttributeConstant.BOOLEAN)
                ||attribute.getType().equals(AttributeConstant.DATE)||attribute.getType().equals(AttributeConstant.DATETIME)
                ||attribute.getType().equals(AttributeConstant.DOUBLE)||attribute.getType().equals(AttributeConstant.INTEGER)
                ||attribute.getType().equals(AttributeConstant.TIME))){
            errorList.add("Loại thuộc tính không hợp lệ");
        }
        if(errorList.size() > 0){
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(attributeService.doInsert(attribute));
    }
    @GetMapping("/page/{page}/{size}")
    public Page<Attribute> getAllAttribute(@PathVariable int page, @PathVariable int size){
        return attributeService.findAll(page,size);
    }
    @GetMapping("/find/{name}")
    public Optional<Attribute> findAttributeByName(@PathVariable String name){
        return attributeService.findByName(name);
    }
    @GetMapping("/get/{id}")
    public Attribute findAttributeById(@PathVariable Long id){
        return attributeService.findById(id);
    }
    @PutMapping("/update/{id}")
    public Attribute updateById(@PathVariable Long id,@RequestBody Attribute attribute){
        return attributeService.doUpdateById(attribute,id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        attributeService.doDeleteById(id);
    }

    @PostMapping("/addAll")
    public List<Attribute> addAll(@RequestBody AttributeAddAllRequest request){
        return attributeService.addAll(request);
    }

    @GetMapping("/findAll")
    public List<Attribute> findAll(){
        return attributeService.findAll();
    }

    @GetMapping("/findByCategoryId/{id}")
    public List<Attribute> findByCategoryId(@PathVariable Long id){
        return attributeService.findByCategoryId(id);
    }
    @PostMapping("/deleteAttributeRequest")
    public void deleteAttributeRequest(@RequestBody DeleteAttributeRequest attribute){
        attributeService.deleteAttributeByAttributeId(attribute);
    }
}
