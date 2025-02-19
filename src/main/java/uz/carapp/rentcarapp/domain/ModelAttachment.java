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
 * A ModelAttachment.
 */
@Entity
@Table(name = "model_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class ModelAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_main")
    private boolean isMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brand" }, allowSetters = true)
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brand" }, allowSetters = true)
    private Attachment attachment;
}
