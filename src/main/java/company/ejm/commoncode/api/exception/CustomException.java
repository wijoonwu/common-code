package company.ejm.commoncode.api.exception;

import company.ejm.commoncode.api.properties.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}