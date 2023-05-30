package company.ejm.commoncode.api.dto;

import company.ejm.commoncode.api.entity.CommonCode;
import lombok.*;

@ToString @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonCodeDto {

    private long id;
    private String code;
    private String name;
    private CodeGroupDto groupDto;

    public CommonCode toEntity(){
        return CommonCode.builder()
                .code(this.code)
                .name(this.name)
                .codeGroup(this.groupDto.toEntity())
                .build();
    }

    public CommonCodeDto(CommonCode commonCode) {
        this.id = commonCode.getId();
        this.code = commonCode.getCode();
        this.name = commonCode.getName();
        this.groupDto = new CodeGroupDto(commonCode.getCodeGroup());
    }
}