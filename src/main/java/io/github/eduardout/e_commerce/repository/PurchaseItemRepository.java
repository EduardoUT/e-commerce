package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.PurchaseItem;
import io.github.eduardout.e_commerce.entity.PurchaseItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, PurchaseItemId> {
    @Modifying
    @Query(value = "DELETE FROM purchase_item pi WHERE pi.purchase.id = :purchaseId")
    void deleteByPurchaseId(@Param("purchaseId") Long purchaseId);
    @Query(value = "SELECT pi FROM purchase_item pi WHERE pi.purchase.id = :purchaseId")
    List<PurchaseItem> findAllByPurchaseId(@Param("purchaseId") Long purchaseId);


}
