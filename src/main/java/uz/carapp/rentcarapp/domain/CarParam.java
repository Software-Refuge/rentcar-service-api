package uz.carapp.rentcarapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A CarParam.
 */
@Entity
@Table(name = "car_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarParam extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private ParamValue paramValue; //SELECT FIELD UCHUN

    @Column(name = "param_val")
    private String paramVal; //INT, FLOAT, DATETIME, DATE, kabi value lar uchun field

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "model", "merchant", "merchantBranch" }, allowSetters = true)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    private Param param;
}
