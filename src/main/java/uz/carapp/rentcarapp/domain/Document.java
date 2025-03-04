package uz.carapp.rentcarapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.carapp.rentcarapp.domain.enumeration.DocTypeEnum;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type")
    private DocTypeEnum docType;

    @Column(name = "given_date")
    private Instant givenDate;

    @Column(name = "issued_date")
    private Instant issuedDate;

    @Column(name = "doc_status")
    private Boolean docStatus;
}
