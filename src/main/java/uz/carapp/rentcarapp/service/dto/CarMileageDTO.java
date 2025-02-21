package uz.carapp.rentcarapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.carapp.rentcarapp.domain.enumeration.MileageEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarMileage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarMileageDTO implements Serializable {

    private Long id;

    private BigDecimal value;

    private MileageEnum unit;

    private Instant date;

    private CarDTO car;
}
