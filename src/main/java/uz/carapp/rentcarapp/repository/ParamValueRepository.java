package uz.carapp.rentcarapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.carapp.rentcarapp.domain.ParamValue;

/**
 * Spring Data JPA repository for the ParamValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamValueRepository extends JpaRepository<ParamValue, Long> {

    @Query(value = "select pv from ParamValue pv where pv.param.id=:paramId")
    Page<ParamValue> findAllByParamId(Pageable pageable,Long paramId);
}
