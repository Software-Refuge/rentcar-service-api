package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.CarAttachment;

/**
 * Spring Data JPA repository for the CarAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarAttachmentRepository extends JpaRepository<CarAttachment, Long> {

    @Modifying
    @Query(value = "UPDATE CarAttachment ca SET ca.isMain = false WHERE ca.isMain=true and ca.car.id=:carId")
    void removeOldMainPhoto(Long carId);


    @Modifying
    @Query(value = "UPDATE CarAttachment ca SET ca.isMain = true WHERE ca.car.id=:carId and ca.attachment.id=:attachmentId")
    void setMainPhoto(Long carId, Long attachmentId);
}
