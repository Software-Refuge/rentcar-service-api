package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.CarTemplateParam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class CarTemplateParamDTO implements Serializable {

    private Long id;

    private String paramVal;

    private CarTemplateDTO carTemplate;

    private ParamDTO param;

    private ParamValueDTO paramValue;

    // prettier-ignore
    @Override
    public String toString() {
        return "CarTemplateParamDTO{" +
            "id=" + getId() +
            ", paramVal='" + getParamVal() + "'" +
            ", carTemplate=" + getCarTemplate() +
            ", param=" + getParam() +
            ", paramValue=" + getParamValue() +
            "}";
    }
}
