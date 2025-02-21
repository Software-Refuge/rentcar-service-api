package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.carapp.rentcarapp.domain.enumeration.MileageEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarMileage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarMileageSaveDTO implements Serializable {

    private Long id;

    private BigDecimal value;

    private MileageEnum unit;

    private Instant date;

    private Long carId;
}
