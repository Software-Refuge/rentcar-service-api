package uz.carapp.rentcarapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A CarTemplateParam.
 */
@Entity
@Table(name = "car_template_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarTemplateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "param_val")
    private String paramVal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "model" }, allowSetters = true)
    private CarTemplate carTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Param param;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "param" }, allowSetters = true)
    private ParamValue paramValue;

    // prettier-ignore
    @Override
    public String toString() {
        return "CarTemplateParam{" +
            "id=" + getId() +
            ", paramVal='" + getParamVal() + "'" +
            "}";
    }
}
