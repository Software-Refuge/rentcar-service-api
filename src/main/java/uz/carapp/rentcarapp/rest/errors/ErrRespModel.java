package uz.carapp.rentcarapp.rest.errors;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrRespModel {

    private HttpStatus status;
    private String detail;
    private LocalDateTime timestamp;
}
