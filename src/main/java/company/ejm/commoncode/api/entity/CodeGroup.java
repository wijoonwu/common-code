package company.ejm.commoncode.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CodeGroup {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToMany(mappedBy = "codeGroup")
    @Builder.Default
    private List<CommonCode> commonCodeList = new ArrayList<>();

    public void updateName(String groupName) {
        this.name = groupName;
    }

    public void addCommonCode(CommonCode commonCode) {
        this.commonCodeList.add(commonCode);
    }

}
