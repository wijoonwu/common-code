package company.ejm.commoncode.api.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusEnum {
    CREATED(201, "CREATED"),
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int statusCode;
    String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(this.statusCode);
    }

}
