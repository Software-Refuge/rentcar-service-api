package uz.carapp.rentcarapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.carapp.rentcarapp.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    void getModelId(Long id);
}
