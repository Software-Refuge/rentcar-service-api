package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.DocAttachment;

import java.util.List;

/**
 * Spring Data JPA repository for the DocAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocAttachmentRepository extends JpaRepository<DocAttachment, Long> {
    @Query(value = "select d from DocAttachment d where d.document.id in :docIds")
    List<DocAttachment> getAttachmentsByDocIds(List<Long> docIds);
}
