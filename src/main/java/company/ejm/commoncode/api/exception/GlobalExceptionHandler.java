package company.ejm.commoncode.api.exception;

import company.ejm.commoncode.api.dto.ErrorDto;
import company.ejm.commoncode.api.model.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static company.ejm.commoncode.api.model.ErrorCode.INTERNAL_SERVER_ERROR;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorDto errorDto = new ErrorDto(errorCode.getStatus(), errorCode.getMsg());
        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(errorCode.getStatus()));
    }
    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception ex) {
        return new ResponseEntity(new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
