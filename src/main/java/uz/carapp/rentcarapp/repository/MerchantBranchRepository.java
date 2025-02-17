package uz.carapp.rentcarapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.carapp.rentcarapp.domain.MerchantBranch;

import java.util.List;
import java.util.Optional;

public interface MerchantBranchRepository extends JpaRepository<MerchantBranch, Long> {

    @Query(value = "select m from MerchantBranch m where (:search is null or lower(m.name) like %:search% or lower(m.phone) like %:search%)")
    Page<MerchantBranch> findAll(String search, Pageable pageable);

    @Query(value = "select m.id from MerchantBranch m " +
                    "where m.merchant.id " +
                       "in (select m.id from Merchant m where m.user.id=:id)")
    List<Long> getBranchIdsByUserId(Long id);

    @Query(value = "select mb from MerchantBranch mb " +
                    "where mb.id=:merchantBranchId " +
                      "and (mb.merchant.user.id=:userId or exists (select 1 from MerchantRole mr where mr.user.id=:userId))")
    Optional<MerchantBranch> getMerchantBranchById(Long userId, Long merchantBranchId);
}
