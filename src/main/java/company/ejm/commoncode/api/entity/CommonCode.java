package company.ejm.commoncode.api.entity;

import company.ejm.commoncode.api.dto.CommonCodeDto;
import lombok.*;

import javax.persistence.*;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CommonCode {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_id")
    private CodeGroup codeGroup;

    public void update(CommonCodeDto reqCodeDto) {
        this.code = reqCodeDto.getCode();
        this.name = reqCodeDto.getName();
        this.codeGroup = reqCodeDto.getGroupDto().toEntity();
    }

    public void setCodeGroup(CodeGroup codeGroup) {
        this.codeGroup = codeGroup;
    }
}
