package org.datn.app.core.repo;

import org.datn.app.core.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p from Product p,Category c where p.status = 1 AND p.category.id = c.id AND c.isTrash = false ORDER BY p.id DESC")
    List<Product> findAll();

    @Query("select p from Product p,Category c where p.status = 1 AND p.brand.id = ?1 AND p.category.id = c.id AND c.isTrash = false ORDER BY p.id DESC")
    List<Product> findByBrandId(Long id);

    // find top 10 sản phẩm mới
    @Query("select p from Product p,Category c where p.status = 1 AND p.category.id = c.id AND c.isTrash = false ORDER BY p.id DESC")
    List<Product> findTop10ByOrderByIdDesc();

    @Query("select p from Product p,Category c where p.status = 1 AND p.category.id = c.id AND c.isTrash = false ORDER BY p.id DESC")
    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "INNER JOIN categories c ON c.category_id = p.category_id " +
            "INNER JOIN product_details pd ON p.product_id = pd.product_id " +
            "INNER JOIN sizes s ON s.size_id = pd.size_id " +
            "LEFT JOIN category_attribute ca ON ca.category_id = c.category_id " +
            "INNER JOIN colors co ON co.color_id = pd.color_id " +
            "WHERE p.status = 1  " +
            "AND (:keyword IS NULL OR MATCH(p.product_name) AGAINST (:keyword  IN BOOLEAN MODE)) " +
            "AND (COALESCE(:brandIdList) IS NULL OR p.brand_id IN (:brandIdList)) " +
            "AND (COALESCE(:categoryIdList) IS NULL OR p.category_id IN (:categoryIdList)) " +
            "AND (COALESCE(:attributeIdList) IS NULL OR ca.attribute_id IN (:attributeIdList)) " +
            "AND (COALESCE(:colorIdList) IS NULL OR pd.color_id IN(:colorIdList))" +
            "AND (COALESCE(:materialList) IS NULL OR p.material IN (:materialList)) " +
            "AND (COALESCE(:modelList) IS NULL OR p.model IN (:modelList)) " +
            "AND (COALESCE(:sizeIdList) IS NULL OR pd.size_id IN (:sizeIdList)) " +
            "AND (:gender IS NULL OR p.gender = :gender) " +
            "AND  c.is_trash = FALSE  ORDER BY p.created_date DESC", nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByFilter(String keyword, List<Long> brandIdList, List<Long> categoryIdList, List<Long> attributeIdList, List<Long> colorIdList, List<String> materialList, List<String> modelList, List<Long> sizeIdList,Integer gender, Pageable pageable);


    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "INNER JOIN categories c ON c.category_id = p.category_id " +
            "INNER JOIN product_details pd ON p.product_id = pd.product_id " +
            "INNER JOIN sizes s ON s.size_id = pd.size_id " +
            "LEFT JOIN category_attribute ca ON ca.category_id = c.category_id " +
            "INNER JOIN colors co ON co.color_id = pd.color_id " +
            "WHERE p.status = 1  " +
            "AND (:keyword IS NULL OR p.product_name like %:keyword%) " +
            "AND (COALESCE(:brandIdList) IS NULL OR p.brand_id IN (:brandIdList)) " +
            "AND (COALESCE(:categoryIdList) IS NULL OR p.category_id IN (:categoryIdList)) " +
            "AND (COALESCE(:attributeIdList) IS NULL OR ca.attribute_id IN (:attributeIdList)) " +
            "AND (COALESCE(:colorIdList) IS NULL OR pd.color_id IN(:colorIdList))" +
            "AND (COALESCE(:materialList) IS NULL OR p.material IN (:materialList)) " +
            "AND (COALESCE(:modelList) IS NULL OR p.model IN (:modelList)) " +
            "AND (COALESCE(:sizeIdList) IS NULL OR pd.size_id IN (:sizeIdList)) " +
            "AND  c.is_trash = FALSE", nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByFilterLike(String keyword, List<Long> brandIdList, List<Long> categoryIdList, List<Long> attributeIdList, List<Long> colorIdList, List<String> materialList, List<String> modelList, List<Long> sizeIdList, Pageable pageable);
}
