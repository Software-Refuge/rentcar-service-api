package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.MerchantRole;
import uz.carapp.rentcarapp.domain.enumeration.MerchantRoleEnum;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the MerchantRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantRoleRepository extends JpaRepository<MerchantRole, Long> {
    @Query("select merchantRole from MerchantRole merchantRole where merchantRole.user.login = ?#{authentication.name}")
    List<MerchantRole> findByUserIsCurrentUser();

    @Query(value = "select m from MerchantRole m where m.user.id=:id")
    List<MerchantRole> findByUserId(Long id);

    @Query(value = "select m from MerchantRole m " +
                    "where m.user.id=:id and m.merchant.id=:merchantId and m.merchantBranch.id=:merchantBranchId")
    List<MerchantRole> findByUserIdAndMerchantIdAndBranchId(Long id, Long merchantId, Long merchantBranchId);

    @Query(value = "select m from MerchantRole m where m.user.id=:id and m.merchant.id=:merchantId and m.merchantRoleType=:merchantRoleEnum")
    List<MerchantRole> findByUserIdAndMerchantIdAndRoleType(Long id, Long merchantId, MerchantRoleEnum merchantRoleEnum);
}
