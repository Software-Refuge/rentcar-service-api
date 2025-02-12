package uz.carapp.rentcarapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.carapp.rentcarapp.domain.MerchantBranch;

import java.util.List;

public interface MerchantBranchRepository extends JpaRepository<MerchantBranch, Long> {

    @Query(value = "select m from MerchantBranch m where (:search is null or lower(m.name) like %:search% or lower(m.phone) like %:search%)")
    Page<MerchantBranch> findAll(String search, Pageable pageable);

    @Query(value = "select m.id from MerchantBranch m " +
                    "where m.merchant.id " +
                       "in (select m.id from Merchant m where m.user.id=:id)")
    List<Long> getBranchIdsByUserId(Long id);
}
