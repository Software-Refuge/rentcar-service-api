package uz.carapp.rentcarapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.List;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Car extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "state_number_plate")
    private String stateNumberPlate;

    @Column(name = "deposit")
    private Integer deposit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brand" }, allowSetters = true)
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "merchant" }, allowSetters = true)
    private MerchantBranch merchantBranch;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    List<CarParam> carParam;
}
