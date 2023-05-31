package company.ejm.commoncode.api.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "서버 에러가 발생했습니다. 고객센터로 문의 바랍니다."),
    CODE_NOT_FOUND(400, "존재하지 않는 코드입니다." ),
    GROUP_NOT_FOUND(400, "존재하지 않는 그룹입니다." ),
    GROUP_NAME_ALREADY_EXIST(409, "이미 존재하는 그룹 이름입니다." ),
    CODE_ALREADY_EXIST(409, "이미 존재하는 코드값입니다." ),
    CODE_NAME_ALREADY_EXIST(409, "이미 존재하는 코드 이름입니다."),
    CANNOT_BE_EMPTY(400, "공백은 입력할 수 없습니다.");

    private final int status;
    private final String msg;
}
