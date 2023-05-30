package company.ejm.commoncode.api.dto;

import company.ejm.commoncode.api.entity.CommonCode;
import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonCodeDto {

    private long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
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
