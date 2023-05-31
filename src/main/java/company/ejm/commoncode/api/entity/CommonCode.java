package company.ejm.commoncode.api.entity;

import company.ejm.commoncode.api.dto.CommonCodeDto;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_id")
    private CodeGroup codeGroup;

    public void update(CommonCodeDto reqCodeDto, CodeGroup codeGroup) {
        this.code = reqCodeDto.getCode();
        this.name = reqCodeDto.getName();
        this.codeGroup = codeGroup;
    }
}
