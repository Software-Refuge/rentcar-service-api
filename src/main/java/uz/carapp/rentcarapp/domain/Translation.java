package uz.carapp.rentcarapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.carapp.rentcarapp.domain.enumeration.LanguageEnum;

import java.io.Serializable;

/**
 * A Translation.
 */
@Entity
@Table(name = "translation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Translation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private LanguageEnum lang;

    @Column(name = "value")
    private String value;

    @Column(name = "description")
    private String description;
}
