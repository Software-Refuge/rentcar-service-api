package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.ModelAttachment;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the ModelAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelAttachmentRepository extends JpaRepository<ModelAttachment, Long> {

    @Modifying
    @Query(value = "UPDATE ModelAttachment ma SET ma.isMain = false WHERE ma.model.id=:modelId and ma.isMain=true")
    void removeOldMainPhoto(Long modelId);

    @Modifying
    @Query(value = "UPDATE ModelAttachment ma SET ma.isMain = true WHERE ma.model.id=:modelId and ma.attachment.id=:attachmentId")
    void setMainPhoto(Long modelId, Long attachmentId);

    @Query(value = "select m from ModelAttachment m where m.model.id in :modelIds and m.isMain=true")
    List<ModelAttachment> getModelMainPhotoByIds(Set<Long> modelIds);

    @Query(value = "select m from ModelAttachment m where m.model.id=:modelId")
    List<ModelAttachment> getModelId(Long modelId);
}
