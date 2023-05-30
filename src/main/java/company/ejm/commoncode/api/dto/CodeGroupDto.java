package company.ejm.commoncode.api.dto;

import company.ejm.commoncode.api.entity.CodeGroup;
import company.ejm.commoncode.api.entity.CommonCode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeGroupDto {
    private long id;
    private String name;
    private List<CommonCodeDto> commonCodeDtoList;

    public CodeGroup toEntity() {
        return CodeGroup.builder()
                .name(this.name)
                .build();
    }


    public CodeGroupDto(CodeGroup codeGroup) {
        this.id = codeGroup.getId();
        this.name = codeGroup.getName();
        this.commonCodeDtoList = codeGroup.getCommonCodeList().stream()
                .map(CommonCodeDto::new)
                .collect(Collectors.toList());
    }
}
