package company.ejm.commoncode.api.dto;

import company.ejm.commoncode.api.entity.CodeGroup;
import company.ejm.commoncode.api.entity.CommonCode;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeGroupDto {
    private long id;
    @NotBlank
    private String name;
    private List<CommonCodeDto> commonCodeList;

    public CodeGroup toEntity() {
        return CodeGroup.builder()
                .name(this.name)
                .build();
    }


    public CodeGroupDto(CodeGroup codeGroup) {
        this.id = codeGroup.getId();
        this.name = codeGroup.getName();
        if (codeGroup.getCommonCodeList() != null) {
            this.commonCodeList = codeGroup.getCommonCodeList().stream()
                    .map(CommonCodeDto::new)
                    .collect(Collectors.toList());
        } else {
            this.commonCodeList = new ArrayList<>();
        }
    }

}
