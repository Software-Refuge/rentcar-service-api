package uz.carapp.rentcarapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A CarTemplate.
 */
@Entity
@Table(name = "car_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @OneToOne
    @JsonIgnoreProperties(value = { "brand" }, allowSetters = true)
    private Model model;

    // prettier-ignore
    @Override
    public String toString() {
        return "CarTemplate{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
