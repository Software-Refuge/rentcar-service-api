package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.MerchantDocument;

import java.util.List;

/**
 * Spring Data JPA repository for the MerchantDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantDocumentRepository extends JpaRepository<MerchantDocument, Long> {

    @Query("""
        SELECT md 
        FROM MerchantDocument md
        JOIN FETCH Document d
        LEFT JOIN FETCH Attachment a
        WHERE md.merchant.id = :merchantId
    """)
    List<MerchantDocument> findAllDocumentsByMerchantId(@Param("merchantId") Long merchantId);
}
