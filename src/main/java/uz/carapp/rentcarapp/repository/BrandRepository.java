package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.Brand;

/**
 * Spring Data JPA repository for the Brand entity.
 */
@SuppressWarnings("unused")
@Repository("brandJpaRepository")
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Modifying
    @Query(value = "update Brand b set b.attachment.id=:attachmentId where b.id=:brandId")
    void updateBrandById(@Param("brandId") Long brandId, @Param("attachmentId") Long attachmentId);
}
