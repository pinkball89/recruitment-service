package vn.unigap.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.unigap.api.dto.out.ApiResponseDTO;
import vn.unigap.api.service.EmployerService;

public class ExceptionHandler {

    public static ResponseEntity<ApiResponseDTO> toApiResponse(Exception e) {
        if (e instanceof ApiException.BadInputException) {
            //Catching our own BadInputException
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO(1, 400, e.getMessage(), null));
        } else {
            //Catching any exception that is not created intentionally
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ApiResponseDTO(1, 500, e.getMessage(), null));
        }
    }
}
